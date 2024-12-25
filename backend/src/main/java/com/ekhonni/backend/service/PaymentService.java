package com.ekhonni.backend.service;

import com.ekhonni.backend.config.SSLCommerzConfig;
import com.ekhonni.backend.enums.TransactionStatus;
import com.ekhonni.backend.exception.InvalidTransactionException;
import com.ekhonni.backend.model.Transaction;
import com.ekhonni.backend.payment.sslcommerz.SSLCommerzInitResponse;
import com.ekhonni.backend.payment.sslcommerz.SSLCommerzValidatorResponse;
import com.ekhonni.backend.payment.sslcommerz.Util;
import com.ekhonni.backend.projection.GatewayResponseProjection;
import com.ekhonni.backend.response.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.TreeMap;

/**
 * Author: Asif Iqbal
 * Date: 12/15/24
 */
@Service
@Slf4j
public class PaymentService {

    private final TransactionService transactionService;
    private final ProjectionFactory projectionFactory;
    private final Util util;
    private final String sslcommerzApiUrl;
    private final String storePassword;
    private final String validatorApiUrl;

    public PaymentService(TransactionService transactionService,
                          ProjectionFactory projectionFactory,
                          Util util,
                          SSLCommerzConfig sslCommerzConfig) {
        this.transactionService = transactionService;
        this.projectionFactory = projectionFactory;
        this.util = util;
        this.sslcommerzApiUrl = sslCommerzConfig.getApiUrl();
        this.storePassword = sslCommerzConfig.getStore_passwd();
        this.validatorApiUrl = sslCommerzConfig.getValidatorApiUrl();
    }

    public ApiResponse<?> initiatePayment(Long bidLogId) {
        Transaction transaction = transactionService.create(bidLogId);
        String requestBody;
        try {
            requestBody = util.getParamsString(transaction,true);
        } catch (UnsupportedEncodingException e) {
            transactionService.deletePermanently(transaction.getId());
            return new ApiResponse<>(false, "Error", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        RestTemplate restTemplate = new RestTemplate();
        SSLCommerzInitResponse response = restTemplate.postForEntity(sslcommerzApiUrl, requestEntity, SSLCommerzInitResponse.class).getBody();
        log.info("Initial response: {}", response);

        if (response != null && "SUCCESS".equals(response.getStatus())) {
            transaction.setSessionKey(response.getSessionkey());
            GatewayResponseProjection responseProjection = projectionFactory.createProjection(GatewayResponseProjection.class, response);
            return new ApiResponse<>(true, "Success", responseProjection, HttpStatus.OK);
        } else {
            transactionService.deletePermanently(transaction.getId());
            String message = "Unknown error";
            if (response != null) {
                message = response.getFailedReason();
            }
            return new ApiResponse<>(false, message, null, HttpStatus.BAD_REQUEST);
        }
    }

    public boolean verifyTransaction(Map<String, String> validatorResponse) {
        SSLCommerzValidatorResponse response = projectionFactory.createProjection(SSLCommerzValidatorResponse.class, validatorResponse);
        long transactionId;
        try {
            transactionId = Long.parseLong(response.getTran_id());
        } catch (NumberFormatException e) {
            log.error(e.getMessage());
            return false;
        }
        Transaction transaction = transactionService.get(transactionId);  // base service handles error if not found

        if (!ipnHashVerify(validatorResponse)) {
            transaction.setStatus(TransactionStatus.valueOf("INVALID"));
            log.error("IPN hash verification failed for transaction_id : {}", validatorResponse.get("tran_id"));
            return false;
        }

        if (!verifyStatus(response)) {
            String status = response.getStatus() == null ? "FAILED" : response.getStatus();
            transaction.setStatus(TransactionStatus.valueOf(status));
            log.error("Transaction status: {}, id: {}", response.getStatus(), response.getTran_id());
            return false;
        }

        if (!verifyAmount(transaction, response)) {
            transaction.setStatus(TransactionStatus.valueOf("INVALID"));
            log.error("Amount don't match for transaction_id: {}", transactionId);
            return false;
        }

        updateTransaction(transaction, response);
        return true;
    }

    private boolean verifyStatus(SSLCommerzValidatorResponse response) {
        return response.getStatus() != null && !"VALID".equals(response.getStatus());
    }

    private boolean verifyAmount(Transaction transaction, SSLCommerzValidatorResponse response) {
        double currencyRate;
        double responseAmount;
        try {
            currencyRate = Double.parseDouble(response.getCurrency_rate());
            responseAmount = Double.parseDouble(response.getAmount());
        } catch (NumberFormatException e) {
            log.error(e.getMessage());
            return false;
        }
        double expectedBdtAmount = transaction.getAmount() * currencyRate;
        double marginOfError = 0.01;
        return response.getCurrency().equals(transaction.getCurrency())
                && response.getCurrency_amount().equals(String.valueOf(transaction.getAmount()))
                && (Math.abs(expectedBdtAmount - responseAmount) <= marginOfError);
    }

    private void updateTransaction(Transaction transaction, SSLCommerzValidatorResponse response) {
        transaction.setStatus(TransactionStatus.valueOf(response.getStatus()));
        transaction.setValidationId(response.getVal_id());
        transaction.setBankTransactionId(response.getBank_tran_id());
    }

    private boolean ipnHashVerify(final Map<String, String> response) {
        if (response.containsKey("verify_sign") && response.containsKey("verify_key")) {
            String verifySign = response.get("verify_sign");
            String verifyKey = response.get("verify_key");

            if (verifySign != null && !verifySign.isEmpty() && verifyKey != null && !verifyKey.isEmpty()) {
                String[] keyList = verifyKey.split(",");
                TreeMap<String, String> sortedMap = new TreeMap<>();

                for (String key : keyList) {
                    if (response.containsKey(key)) {
                        sortedMap.put(key, response.get(key));
                    }
                }
                String hashedPass = md5(storePassword);
                sortedMap.put("store_passwd", hashedPass);

                StringBuilder hashStringBuilder = new StringBuilder();
                for (Map.Entry<String, String> entry : sortedMap.entrySet()) {
                    hashStringBuilder.append(entry.getKey())
                            .append("=")
                            .append(entry.getValue())
                            .append("&");
                }
                if (!hashStringBuilder.isEmpty()) {
                    hashStringBuilder.setLength(hashStringBuilder.length() - 1);
                }
                String generatedHash = md5(hashStringBuilder.toString());
                return generatedHash.equals(verifySign);
            }
        }
        return false;
    }

    private String md5(String s) {
        byte[] bytesOfMessage = s.getBytes(StandardCharsets.UTF_8);
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            log.error(e.getMessage());
            throw new RuntimeException("Error processing transaction.");
        }
        byte[] theDigest = md.digest(bytesOfMessage);
        StringBuilder sb = new StringBuilder();
        for (byte b : theDigest) {
            sb.append(Integer.toHexString((b & 0xFF) | 0x100), 1, 3);
        }
        return sb.toString();
    }

}
