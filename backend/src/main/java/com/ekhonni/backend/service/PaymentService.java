package com.ekhonni.backend.service;

import com.ekhonni.backend.config.SSLCommerzConfig;
import com.ekhonni.backend.enums.TransactionStatus;
import com.ekhonni.backend.exception.InvalidTransactionException;
import com.ekhonni.backend.exception.SSLCommerzPaymentException;
import com.ekhonni.backend.interceptor.PaymentLoggingInterceptor;
import com.ekhonni.backend.model.Transaction;
import com.ekhonni.backend.payment.sslcommerz.*;
import com.ekhonni.backend.projection.GatewayResponseProjection;
import com.ekhonni.backend.response.ApiResponse;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
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
    private final String storeId;
    private final String storePassword;
    private final String validatorApiUrl;
    private final RestTemplate restTemplate;

    public PaymentService(TransactionService transactionService,
                          ProjectionFactory projectionFactory,
                          Util util,
                          SSLCommerzConfig sslCommerzConfig) {
        this.transactionService = transactionService;
        this.projectionFactory = projectionFactory;
        this.util = util;
        this.sslcommerzApiUrl = sslCommerzConfig.getApiUrl();
        this.storeId = sslCommerzConfig.getStore_id();
        this.storePassword = sslCommerzConfig.getStore_passwd();
        this.validatorApiUrl = sslCommerzConfig.getValidatorApiUrl();
        this.restTemplate = createRestTemplate();
    }

    private RestTemplate createRestTemplate() {
        RestTemplate template = new RestTemplate();
        template.setInterceptors(Collections.singletonList(new PaymentLoggingInterceptor()));
        return template;
    }

    public ApiResponse<?> initiatePayment(Long bidLogId) {
        try {
            Transaction transaction = transactionService.create(bidLogId);
            String requestBody = prepareRequestBody(transaction);
            InitialResponse response = sendPaymentRequest(requestBody);
            return handleInitialResponse(response, transaction);
        } catch (Exception e) {
            log.error("Payment initiation failed: {}", e.getMessage());
            throw new SSLCommerzPaymentException("Payment initiation failed");
        }
    }

    private String prepareRequestBody(Transaction transaction) throws UnsupportedEncodingException {
        return util.getParamsString(transaction, true);
    }

    private InitialResponse sendPaymentRequest(String requestBody) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<InitialResponse> responseEntity = restTemplate.postForEntity(
                sslcommerzApiUrl,
                requestEntity,
                InitialResponse.class
        );

        InitialResponse response = responseEntity.getBody();
        log.info("Initial response: {}", response);
        validateInitialResponse(response);
        return response;
    }

    private ApiResponse<?> handleInitialResponse(InitialResponse response, Transaction transaction) {
        if (response != null && "SUCCESS".equals(response.getStatus())) {
            transaction.setSessionKey(response.getSessionkey());
            GatewayResponseProjection responseProjection = projectionFactory.createProjection(
                    GatewayResponseProjection.class,
                    response
            );
            return new ApiResponse<>(true, "Success", responseProjection, HttpStatus.OK);
        } else {
            transactionService.deletePermanently(transaction.getId());
            String message = response != null ? response.getFailedReason() != null ? response.getFailedReason() : "Unknown error" : "Null response";
            return new ApiResponse<>(false, message, null, HttpStatus.BAD_REQUEST);
        }
    }

    public void verifyTransaction(Map<String, String> ipnResponse) {
        if (ipnResponse == null) {
            log.error("Ipn response is null");
            throw new SSLCommerzPaymentException();
        }
        IpnResponse response = projectionFactory.createProjection(IpnResponse.class, ipnResponse);
        log.info("IPN response: {}", response);
        Transaction transaction = getTransaction(response.getTran_id());

        if (!ipnHashVerify(ipnResponse)) {
            updateStatus(transaction, "INVALID");
            log.error("IPN hash verification failed for transaction_id : {}", ipnResponse.get("tran_id"));
            throw new SSLCommerzPaymentException();
        }

        if (!verifyStatus(response.getStatus())) {
            String status = response.getStatus() == null ? "NO_STATUS" : response.getStatus();
            updateStatus(transaction, status);
            log.error("transaction_id: {}, status: {}", response.getTran_id(), response.getStatus());
            throw new SSLCommerzPaymentException();
        }

        if (!verifyAmount(transaction, response)) {
            updateStatus(transaction, "INVALID");
            log.error("Amount or currency don't match for transaction_id: {}", response.getTran_id());
            throw new SSLCommerzPaymentException();
        }

        if (!validate(response.getVal_id())) {
            log.error("Validation failed for transaction_id: {}", response.getTran_id());
            throw new SSLCommerzPaymentException();
        }

    }

    private boolean validate(String validationId) {
        String validationUrl = validatorApiUrl + "?val_id=" + validationId
                + "&store_id=" + storeId + "&store_passwd=" + storePassword + "&v=1&format=json";

        log.info("Validation URL: {}", validationUrl);
        Map validationResponse = restTemplate.getForObject(validationUrl, Map.class);
        if (validationResponse == null) {
            return false;
        }
        ValidationResponse response = projectionFactory.createProjection(ValidationResponse.class, validationResponse);
        log.info("Validation response: {}", validationResponse);

        Transaction transaction = getTransaction(response.getTran_id());

        if (!verifyStatus(response.getStatus())) {
            String status = response.getStatus() == null ? "NO_STATUS" : response.getStatus();
            updateStatus(transaction, status);
            log.error("Validation transaction_id: {}, status: {}", response.getTran_id(), response.getStatus());
            return false;
        }

        if (!verifyAmount(transaction, response)) {
            updateStatus(transaction, "INVALID");
            log.error("Validation amount or currency don't match for transaction_id: {}", response.getTran_id());
            return false;
        }

        updateTransaction(transaction, response);
        return true;
    }

    private Transaction getTransaction(String trxId) {
        long transactionId;
        try {
            transactionId = Long.parseLong(trxId);
        } catch (NumberFormatException e) {
            log.error(e.getMessage());
            throw new InvalidTransactionException();
        }
        return transactionService.get(transactionId);
    }

    private void validateInitialResponse(InitialResponse response) {
        if (response == null) {
            throw new SSLCommerzPaymentException("Null response from payment gateway");
        }
        if (response.getStatus() == null) {
            throw new SSLCommerzPaymentException("Invalid response status");
        }
    }

    private boolean verifyStatus(String status) {
        return "VALID".equals(status) || "VALIDATED".equals(status);
    }

    private boolean verifyAmount(Transaction transaction, PaymentResponse response) {
        try {
            double currencyRate = Double.parseDouble(response.getCurrency_rate());
            double responseAmount = Double.parseDouble(response.getCurrency_amount());
            double responseBdtAmount = Double.parseDouble(response.getAmount());
            double expectedBdtAmount = transaction.getAmount() * currencyRate;
            log.info("Expected BDT amount: {}, Response Amount: {}, Difference: {}", expectedBdtAmount, responseBdtAmount, Math.abs(expectedBdtAmount - responseBdtAmount));
            double marginOfError = 0.01;
            return response.getCurrency_type().equals(transaction.getCurrency())
                    && responseAmount == transaction.getAmount()
                    && (Math.abs(expectedBdtAmount - responseBdtAmount) <= marginOfError);
        } catch (NumberFormatException e) {
            log.error(e.getMessage());
            return false;
        }
    }

    @Modifying
    @Transactional
    private void updateStatus(Transaction transaction, String status) {
        transaction.setStatus(TransactionStatus.valueOf(status));
    }

    @Modifying
    @Transactional
    private void updateTransaction(Transaction transaction, ValidationResponse response) {
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
