package com.ekhonni.backend.service;

import com.ekhonni.backend.config.SSLCommerzConfig;
import com.ekhonni.backend.enums.BidStatus;
import com.ekhonni.backend.enums.TransactionStatus;
import com.ekhonni.backend.exception.InitiatePaymentException;
import com.ekhonni.backend.exception.InvalidTransactionException;
import com.ekhonni.backend.model.Account;
import com.ekhonni.backend.model.Bid;
import com.ekhonni.backend.model.Transaction;
import com.ekhonni.backend.payment.sslcommerz.*;
import com.ekhonni.backend.projection.PaymentResponseProjection;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

/**
 * Author: Asif Iqbal
 * Date: 12/15/24
 */
@Service
@AllArgsConstructor
@Slf4j
public class PaymentService {

    private final TransactionService transactionService;
    private final BidService bidService;
    private final ProjectionFactory projectionFactory;
    private final Util util;
    private final SSLCommerzConfig sslCommerzConfig;
    private final RestClient restClient;

    @Modifying
    @Transactional
    @CircuitBreaker(name = "initiatePayment", fallbackMethod = "initiatePaymentFallback")
    @Retry(name = "retryPayment", fallbackMethod = "retryPaymentFallback")
    public PaymentResponseProjection initiatePayment(Long bidId) throws Exception {
        Bid bid = bidService.get(bidId)
                .orElseThrow(() -> {
                    log.warn("Payment request for invalid bid: {}", bidId);
                    return new InvalidTransactionException();
                });
        if (bid.getStatus() == BidStatus.PAID) {
            log.warn("Duplicate payment request for bid: {}", bidId);
            throw new InitiatePaymentException();
        }
        if (bid.getStatus() != BidStatus.ACCEPTED) {
            log.warn("Payment request for unaccepted bid: {}", bidId);
            throw new InvalidTransactionException();
        }

        Transaction transaction = transactionService.findByBidId(bidId)
                .orElse(transactionService.create(bid));

        log.info("Transaction : {}", transaction);

        String requestBody = prepareRequestBody(transaction);
        InitialResponse response = sendPaymentRequest(requestBody);
        validateInitialResponse(response);
        transaction.setSessionKey(response.getSessionkey());
        log.info("Transaction after setting session key: {}", transaction);
        return projectionFactory.createProjection(
                PaymentResponseProjection.class,
                response
        );
    }

    public PaymentResponseProjection initiatePaymentFallback(Long bidId, Exception e) {
        log.warn("Initiate payment fallback triggered for bid {} due to error: {}", bidId, e.getMessage());
        throw new InitiatePaymentException("Payment service not available");
    }

    public PaymentResponseProjection retryPaymentFallback(Long bidId, Exception e) {
        log.warn("All retries failed for bid {} due to error: {}", bidId, e.getMessage());
        throw new InitiatePaymentException("Payment initiation failed");
    }

    private String prepareRequestBody(Transaction transaction) throws UnsupportedEncodingException {
        return util.getParamsString(transaction, true);
    }

    private InitialResponse sendPaymentRequest(String requestBody) {
        return restClient.post()
                .uri(sslCommerzConfig.getApiUrl())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(requestBody)
                .retrieve()
                .body(InitialResponse.class);
    }

    public void verifyTransaction(Map<String, String> ipnResponse, HttpServletRequest request) {
        String gatewayIpAddress = getGatewayIpAddress(request);
        log.info("Payment gateway ip address: {}", gatewayIpAddress);

        if (!sslCommerzConfig.getAllowedIps().contains(gatewayIpAddress)) {
            log.warn("Unknown payment gateway ip address: {}", gatewayIpAddress);
            throw new InvalidTransactionException();
        }

        if (ipnResponse == null) {
            log.warn("Null ipn response in payment");
            throw new InvalidTransactionException();
        }
        IpnResponse response = util.extractIpnResponse(ipnResponse);
        log.info("Ipn response: {}", response);

        Transaction transaction = getTransactionFromResponse(response.getTranId());

        if (!ipnHashVerify(ipnResponse)) {
            updateFailedTransactionStatus(transaction, "INVALID");
            log.warn("IPN signature verification failed for transaction : {}", ipnResponse.get("tran_id"));
            throw new InvalidTransactionException();
        }

        if (!verifyPaymentResponse(transaction, response)) {
            updateFailedTransactionStatus(transaction, response.getStatus());
            log.warn("Ipn response parameters don't match for transaction: {}", response.getTranId());
            throw new InvalidTransactionException();
        }

        if (!validate(response.getValId())) {
            log.warn("Validation failed for transaction: {}", response.getTranId());
            throw new InvalidTransactionException();
        }
    }

    private ValidationResponse getValidationResponse(String validationId) {
        String validationUrl = sslCommerzConfig.getValidatorApiUrl() + "?val_id=" + validationId
                + "&store_id=" + sslCommerzConfig.getStoreId()
                + "&store_passwd=" + sslCommerzConfig.getStorePassword()
                + "&v=1&format=json";

        return restClient.get()
                .uri(validationUrl)
                .retrieve()
                .body(ValidationResponse.class);
    }

    private boolean validate(String validationId) {
        ValidationResponse response = getValidationResponse(validationId);
        if (response == null) {
            return false;
        }

        Transaction transaction = getTransactionFromResponse(response.getTranId());

        if (!verifyPaymentResponse(transaction, response)) {
            updateFailedTransactionStatus(transaction, response.getStatus());
            log.warn("Validation response parameters don't match for transaction: {}", response.getTranId());
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
        return transactionService.get(transactionId)
                .orElseThrow(InvalidTransactionException::new);
    }

    private void validateInitialResponse(InitialResponse response) {
        if (response == null || !"SUCCESS".equals(response.getStatus())) {
            throw new InitiatePaymentException("Payment initiation error");
        }
    }

    private boolean verifyPaymentResponse(Transaction transaction, PaymentResponse response) {
        if (!"VALID".equals(response.getStatus()) && !"VALIDATED".equals(response.getStatus())) {
            return false;
        }
        try {
            double currencyRate = Double.parseDouble(response.getCurrencyRate());
            double responseAmount = Double.parseDouble(response.getCurrencyAmount());
            double responseBdtAmount = Double.parseDouble(response.getAmount());
            double expectedBdtAmount = transaction.getAmount() * currencyRate;
            double marginOfError = 0.01;
            return response.getCurrencyType().equals(transaction.getCurrency())
                    && responseAmount == transaction.getAmount()
                    && (Math.abs(expectedBdtAmount - responseBdtAmount) <= marginOfError);
        } catch (NumberFormatException e) {
            log.error("Invalid number format in transaction: {}", e.getMessage());
            return false;
        }
    }

    @Modifying
    @Transactional
    private void updateFailedTransactionStatus(Transaction transaction, String status) {
        if (status == null || status.equals("VALID")) {
            status = "INVALID";
        }
        transaction.setStatus(TransactionStatus.valueOf(status));
    }

    @Modifying
    @Transactional
    private void updateTransaction(Transaction transaction, ValidationResponse response) {
        transaction.setStatus(TransactionStatus.valueOf(response.getStatus()));
        transaction.setValidationId(response.getValId());
        transaction.setBankTransactionId(response.getBankTranId());
        transaction.getBid().setStatus(BidStatus.PAID);

        Account buyerAccount = transaction.getBid().getBidder().getAccount();
        Account sellerAccount = transaction.getBid().getProduct().getSeller().getAccount();
        buyerAccount.setBalance(buyerAccount.getBalance() - transaction.getAmount());
        sellerAccount.setBalance(sellerAccount.getBalance() + transaction.getAmount());
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
                String hashedPass = md5(sslCommerzConfig.getStorePassword());
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
