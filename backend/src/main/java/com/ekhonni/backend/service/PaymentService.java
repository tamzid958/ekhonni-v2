package com.ekhonni.backend.service;

import com.ekhonni.backend.config.SSLCommerzConfig;
import com.ekhonni.backend.enums.BidStatus;
import com.ekhonni.backend.enums.TransactionStatus;
import com.ekhonni.backend.exception.payment.InitiatePaymentException;
import com.ekhonni.backend.exception.payment.InvalidTransactionException;
import com.ekhonni.backend.model.Bid;
import com.ekhonni.backend.model.Transaction;
import com.ekhonni.backend.payment.sslcommerz.*;
import com.ekhonni.backend.util.DomainUtil;
import com.ekhonni.backend.util.SslcommerzUtil;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.MaxRetriesExceededException;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.concurrent.CircuitBreakingException;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
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
    private final SslcommerzUtil sslcommerzUtil;
    private final SSLCommerzConfig sslCommerzConfig;
    private final RestClient restClient;
    private static final double CURRENCY_CONVERSION_TOLERANCE = 0.01;


    @CircuitBreaker(name = "initiatePayment", fallbackMethod = "initiatePaymentFallback")
    @Retry(name = "retryPayment")
    public InitiatePaymentResponse initiatePayment(Long bidId) throws Exception {
        Bid bid = bidService.get(bidId)
                .orElseThrow(() -> {
                    log.warn("Payment request for invalid bid: {}", bidId);
                    return new InvalidTransactionException();
                });

        verifyBid(bid);

        Transaction transaction = transactionService.findByBidId(bidId)
                .orElseGet(() -> transactionService.create(bid));

        String requestBody = prepareRequestBody(transaction);
        InitialResponse response = sendPaymentRequest(requestBody);
        verifyInitialResponse(response);

        transactionService.updateSessionKey(transaction, response.getSessionkey());
        return new InitiatePaymentResponse(response.getGatewayPageURL());
    }

    public InitiatePaymentResponse initiatePaymentFallback(Long bidId, Exception e) {

        if (e instanceof InvalidTransactionException) {
            throw (InvalidTransactionException) e;
        }
        if (e instanceof InitiatePaymentException || e instanceof RestClientException) {
            log.warn("Initiate payment fallback triggered for bid {} due to error: {}", bidId, e.getMessage());
            throw new InitiatePaymentException("Payment initiation failed");
        }
        if (e instanceof CallNotPermittedException) {
            log.warn("Circuit breaker open for bid {}", bidId);
            throw new CircuitBreakingException("Payment service not available");
        }
        if (e instanceof MaxRetriesExceededException) {
            Transaction transaction = transactionService.findByBidId(bidId)
                    .orElseThrow(InvalidTransactionException::new);
            transactionService.updateStatus(transaction, TransactionStatus.INITIATION_FAILED);

            log.warn("All retries failed for bid {}: {}", bidId, e.getMessage());
            throw new InitiatePaymentException("Payment initiation failed");
        }

        log.error("Unexpected payment error for bid {}: {}", bidId, e.getMessage());
        throw new InitiatePaymentException("Payment processing failed");
    }

    private String prepareRequestBody(Transaction transaction) throws UnsupportedEncodingException {
        return sslcommerzUtil.getParamsString(transaction, true);
    }

    private InitialResponse sendPaymentRequest(String requestBody) {
        return restClient.post()
                .uri(sslCommerzConfig.getApiUrl())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(requestBody)
                .retrieve()
                .body(InitialResponse.class);
    }

    private void verifyBid(Bid bid) {
        if (bid.getStatus() == BidStatus.PAID) {
            log.warn("Duplicate payment request for bid: {}", bid.getId());
            throw new InvalidTransactionException();
        }
        if (bid.getStatus() != BidStatus.ACCEPTED) {
            log.warn("Payment request for unaccepted bid: {}", bid.getId());
            throw new InvalidTransactionException();
        }
    }

    public void verifyTransaction(Map<String, String> ipnResponse, HttpServletRequest request) {
        if (ipnResponse == null) {
            log.warn("Null ipn response in payment");
            return;
        }
        String origin = request.getHeader("Origin");
        String referer = request.getHeader("Referer");
        String host = request.getHeader("Host");
        String server = request.getServerName();
        log.info("Request from Server: {}, Origin: {}, Referer: {}, Host: {}", server, origin, referer, host);

        log.info("Domain name: {}: ", DomainUtil.extractDomain(request));

        String gatewayIpAddress = getGatewayIpAddress(request);
        log.info("Payment gateway ip address: {}", gatewayIpAddress);

        if (!sslCommerzConfig.getAllowedIps().contains(gatewayIpAddress)) {
            log.warn("Unknown payment gateway ip address: {}", gatewayIpAddress);
            throw new InvalidTransactionException();
        }

        IpnResponse response = sslcommerzUtil.extractIpnResponse(ipnResponse);
        log.info("Ipn response: {}", response);

        Transaction transaction = getDBTransactionFromResponse(response.getTranId());

        if (!ipnHashVerify(ipnResponse)) {
            transactionService.updateStatus(transaction, TransactionStatus.SIGNATURE_MISMATCH);
            log.warn("IPN signature verification failed for transaction : {}", response.getTranId());
            throw new InvalidTransactionException();
        }

        String status = response.getStatus() == null ? "NO_STATUS" : response.getStatus();
        if (!("VALID".equals(status) || "VALIDATED".equals(status))) {
            transactionService.updateStatus(transaction, TransactionStatus.valueOf(status));
            log.warn("Unsuccessful transaction: {}, status: {}", transaction.getId(), status);
            return;
        }

        // handle risk_level = SUCCESS_WITH_RISK

        if (!verifyTransactionParameters(transaction, response)) {
            log.warn("Parameters don't match for transaction: {}", transaction.getId());
            validateTransaction(response.getValId());
            throw new InvalidTransactionException();
        }

        if (!validateTransaction(response.getValId())) {
            log.warn("Validation failed for transaction: {}", transaction.getId());
            throw new InvalidTransactionException();
        }
    }

    @Modifying
    @Transactional
    private ValidationResponse getValidationResponse(String validationId) {
        String validationUrl = sslCommerzConfig.getValidatorApiUrl()
                + "?val_id=" + validationId
                + "&store_id=" + sslCommerzConfig.getStoreId()
                + "&store_passwd=" + sslCommerzConfig.getStorePassword()
                + "&v=1&format=json";

        return restClient.get()
                .uri(validationUrl)
                .retrieve()
                .body(ValidationResponse.class);
    }

    private boolean validateTransaction(String validationId) {
        ValidationResponse response = getValidationResponse(validationId);
        if (response == null) {
            return false;
        }
        Transaction transaction = getDBTransactionFromResponse(response.getTranId());
        if (!verifyTransactionParameters(transaction, response)) {
            transactionService.updateStatus(transaction, TransactionStatus.PARAMETERS_MISMATCH);
            transactionService.updateTransaction(transaction, response);
            log.warn("Validation response parameters don't match for transaction: {}", response.getTranId());
            return false;
        }
        transactionService.updateValidatedTransaction(transaction, response);
        return true;
    }

    private Transaction getDBTransactionFromResponse(String trxId) {
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

    private void verifyInitialResponse(InitialResponse response) {
        if (response == null || !"SUCCESS".equals(response.getStatus())) {
            throw new InitiatePaymentException("Payment initiation error");
        }
    }

    private boolean hasNullInRequiredParameters(PaymentResponse response) {
        return response.getCurrencyRate() == null ||
            response.getCurrencyAmount() == null ||
            response.getAmount() == null ||
            response.getCurrencyType() == null;
    }

    private boolean verifyTransactionParameters(Transaction transaction, PaymentResponse response) {
        if (hasNullInRequiredParameters(response)) {
            log.warn("Null value in required parameters of payment response for transaction : {}", transaction.getId());
            return false;
        }
        try {
            double currencyRate = Double.parseDouble(response.getCurrencyRate());
            double responseAmount = Double.parseDouble(response.getCurrencyAmount());
            double responseBdtAmount = Double.parseDouble(response.getAmount());
            double expectedBdtAmount = transaction.getAmount() * currencyRate;
            return response.getCurrencyType().equals(transaction.getCurrency())
                    && responseAmount == transaction.getAmount()
                    && (Math.abs(expectedBdtAmount - responseBdtAmount) <= CURRENCY_CONVERSION_TOLERANCE);
        } catch (NumberFormatException e) {
            log.warn("Invalid number format in transaction: {}", e.getMessage());
            return false;
        }
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
