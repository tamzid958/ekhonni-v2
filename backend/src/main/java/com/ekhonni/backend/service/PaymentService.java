package com.ekhonni.backend.service;

import com.ekhonni.backend.config.SSLCommerzConfig;
import com.ekhonni.backend.enums.TransactionStatus;
import com.ekhonni.backend.exception.InitiatePaymentException;
import com.ekhonni.backend.exception.InvalidTransactionException;
import com.ekhonni.backend.model.Transaction;
import com.ekhonni.backend.payment.sslcommerz.*;
import com.ekhonni.backend.projection.PaymentResponseProjection;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Set;
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
    private final String paymentApiUrl;
    private final String storeId;
    private final String storePassword;
    private final String validatorApiUrl;
    private final RestClient restClient;
    private final Set<String> allowedIps;

    public PaymentService(TransactionService transactionService,
                          ProjectionFactory projectionFactory,
                          Util util,
                          SSLCommerzConfig sslCommerzConfig,
                          RestClient restClient) {
        this.transactionService = transactionService;
        this.projectionFactory = projectionFactory;
        this.util = util;
        this.paymentApiUrl = sslCommerzConfig.getApiUrl();
        this.storeId = sslCommerzConfig.getStoreId();
        this.storePassword = sslCommerzConfig.getStorePassword();
        this.validatorApiUrl = sslCommerzConfig.getValidatorApiUrl();
        this.restClient = restClient;
        this.allowedIps = sslCommerzConfig.getAllowedIps();
    }

    @CircuitBreaker(name = "initiatePayment", fallbackMethod = "initiatePaymentFallback")
    @Retry(name = "initiatePayment", fallbackMethod = "initiatePaymentFallback")
    public PaymentResponseProjection initiatePayment(Long bidId) throws UnsupportedEncodingException {
        try {
            Transaction transaction = transactionService.create(bidId);
            String requestBody = prepareRequestBody(transaction);
            InitialResponse response = sendPaymentRequest(requestBody);
            validateInitialResponse(response);
            return handleInitialResponse(response, transaction);
        } catch (Exception e) {
            log.warn("Error processing transaction for bid {}: {}", bidId, e.getMessage());
            transactionService.deletePermanentlyByBidId(bidId);
            throw e;
        }
    }

    public PaymentResponseProjection initiatePaymentFallback(Long bidId, Throwable throwable) {
        log.warn("Fallback triggered for bid {} due to error: {}", bidId, throwable.getMessage());
        throw new InitiatePaymentException("Payment error");
    }

    private String prepareRequestBody(Transaction transaction) throws UnsupportedEncodingException {
        return util.getParamsString(transaction, true);
    }

    private InitialResponse sendPaymentRequest(String requestBody) {
        return restClient.post()
                .uri(paymentApiUrl)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(requestBody)
                .retrieve()
                .body(InitialResponse.class);
    }

    private PaymentResponseProjection handleInitialResponse(InitialResponse response, Transaction transaction) {
        if (response != null && "SUCCESS".equals(response.getStatus())) {
            transaction.setSessionKey(response.getSessionkey());
            return projectionFactory.createProjection(
                    PaymentResponseProjection.class,
                    response
            );
        } else {
            throw new InitiatePaymentException("Initial response not successful");
        }
    }

    public void verifyTransaction(Map<String, String> ipnResponse, HttpServletRequest request) {
        String gatewayIpAddress = getGatewayIpAddress(request);
        log.info("Payment gateway ip address: {}", gatewayIpAddress);
        if (!allowedIps.contains(gatewayIpAddress)) {
            log.warn("Unknown payment gateway ip address: {}", gatewayIpAddress);
            throw new InvalidTransactionException();
        }

        if (ipnResponse == null) {
            log.warn("Ipn response is null");
            throw new InvalidTransactionException();
        }
        IpnResponse response = util.extractIpnResponse(ipnResponse);
        log.info("Ipn response: {}", response);

        Transaction transaction = getTransactionFromResponse(response.getTranId());

        if (!ipnHashVerify(ipnResponse)) {
            updateStatus(transaction, "INVALID");
            log.warn("IPN signature verification failed for transaction_id : {}", ipnResponse.get("tran_id"));
            throw new InvalidTransactionException();
        }

        if (!verifyStatus(response.getStatus())) {
            String status = response.getStatus() == null ? "NO_STATUS" : response.getStatus();
            updateStatus(transaction, status);
            log.warn("transaction_id: {}, status: {}", response.getTranId(), response.getStatus());
            throw new InvalidTransactionException();
        }

        if (!verifyAmount(transaction, response)) {
            updateStatus(transaction, "INVALID");
            log.warn("Amount or currency don't match for transaction_id: {}", response.getTranId());
            throw new InvalidTransactionException();
        }

        if (!validate(response.getValId())) {
            log.warn("Validation failed for transaction_id: {}", response.getTranId());
            throw new InvalidTransactionException();
        }
    }

    private boolean validate(String validationId) {
        String validationUrl = validatorApiUrl + "?val_id=" + validationId
                + "&store_id=" + storeId
                + "&store_passwd=" + storePassword
                + "&v=1&format=json";

        ValidationResponse response = restClient.get()
                .uri(validationUrl)
                .retrieve()
                .body(ValidationResponse.class);

        if (response == null) {
            return false;
        }

        Transaction transaction = getTransactionFromResponse(response.getTranId());

        if (!verifyStatus(response.getStatus())) {
            String status = response.getStatus() == null ? "NO_STATUS" : response.getStatus();
            updateStatus(transaction, status);
            log.warn("Validation transaction_id: {}, status: {}",
                    response.getTranId(), response.getStatus());
            return false;
        }

        if (!verifyAmount(transaction, response)) {
            updateStatus(transaction, "INVALID");
            log.warn("Validation amount or currency don't match for transaction_id: {}",
                    response.getTranId());
            return false;
        }

        updateTransaction(transaction, response);
        return true;
    }

    private Transaction getTransactionFromResponse(String trxId) {
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
            throw new InitiatePaymentException("No response from payment gateway");
        }
        if (response.getStatus() == null) {
            throw new InitiatePaymentException("Invalid response status");
        }
    }

    private boolean verifyStatus(String status) {
        return "VALID".equals(status) || "VALIDATED".equals(status);
    }

    private boolean verifyAmount(Transaction transaction, PaymentResponse response) {
        try {
            double currencyRate = Double.parseDouble(response.getCurrencyRate());
            double responseAmount = Double.parseDouble(response.getCurrencyAmount());
            double responseBdtAmount = Double.parseDouble(response.getAmount());
            double expectedBdtAmount = transaction.getAmount() * currencyRate;
            log.info("Expected BDT amount: {}, Response Amount: {}, Difference: {}", expectedBdtAmount, responseBdtAmount, Math.abs(expectedBdtAmount - responseBdtAmount));
            double marginOfError = 0.01;
            return response.getCurrencyType().equals(transaction.getCurrency())
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
        transaction.setValidationId(response.getValId());
        transaction.setBankTransactionId(response.getBankTranId());
    }

    private String getGatewayIpAddress(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0].trim();
        }
        return request.getRemoteAddr();
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
