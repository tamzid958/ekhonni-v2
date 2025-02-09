package com.ekhonni.backend.service;

import com.ekhonni.backend.config.SSLCommerzConfig;
import com.ekhonni.backend.dto.transaction.TransactionQueryDTO;
import com.ekhonni.backend.enums.BidStatus;
import com.ekhonni.backend.enums.TransactionStatus;
import com.ekhonni.backend.exception.payment.*;
import com.ekhonni.backend.model.Bid;
import com.ekhonni.backend.model.Transaction;
import com.ekhonni.backend.payment.sslcommerz.*;
import com.ekhonni.backend.util.HashUtil;
import com.ekhonni.backend.util.HttpUtil;
import com.ekhonni.backend.util.SslcommerzUtil;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
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


    @Retry(name = "retryPayment", fallbackMethod = "initiatePaymentFallback")
    public InitiatePaymentResponse initiatePayment(Long bidId) throws Exception {
        Bid bid = bidService.get(bidId)
                .orElseThrow(() -> {
                    log.warn("Payment request for invalid bid: {}", bidId);
                    return new InvalidTransactionRequestException();
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

    public InitiatePaymentResponse initiatePaymentFallback(Long bidId, Exception e) throws Exception {

        if (e instanceof InvalidTransactionRequestException) {
            throw (InvalidTransactionRequestException) e;
        }

        log.warn("Retry payment fallback triggered for bid {} due to error: {}", bidId, e.getMessage());

        Transaction transaction = transactionService.findByBidId(bidId)
                .orElseThrow(() -> new TransactionNotFoundException("Transaction not found"));
        transactionService.updateStatus(transaction, TransactionStatus.INITIATION_FAILED);

        if (e instanceof RestClientException) {
            throw new InitiatePaymentException("Connection error");
        }

        throw e;
    }

    private void verifyBid(Bid bid) {
        if (bid.getStatus() == BidStatus.PAID) {
            log.warn("Duplicate payment request for bid: {}", bid.getId());
            throw new InvalidTransactionRequestException();
        }
        if (bid.getStatus() != BidStatus.ACCEPTED) {
            log.warn("Payment request for unaccepted bid: {}", bid.getId());
            throw new InvalidTransactionRequestException();
        }
    }

    private void verifyInitialResponse(InitialResponse response) {
        if (response == null || !"SUCCESS".equals(response.getStatus())) {
            throw new InitiatePaymentException("Payment initiation error");
        }
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

    public void verifyTransaction(Map<String, String> ipnResponse, HttpServletRequest request) {

//        String protocol = request.getHeader("x-forwarded-proto");
//        String hostName = HttpUtil.getHostName(HttpUtil.getIpAddress(request));
//        log.info("Protocol: {}, domain name: {}", protocol, hostName);
//
//        if (!"https".equals(protocol)) {
//            log.warn("Connection not secure for transaction in request: {}", request.getRemoteHost());
//            throw new InvalidTransactionException();  // when not using ngrok
//        }
//
//        if (!sslCommerzConfig.getDomain().equals(hostName)) {
//            log.warn("Transaction request from unknown domain in request: {}", request.getRemoteHost());
//            throw new InvalidTransactionException();   // when not using ngrok
//        }

        String gatewayIpAddress = HttpUtil.getIpAddress(request);
        if (!sslCommerzConfig.getAllowedIps().contains(gatewayIpAddress)) {
            log.warn("Payment request from unknown ip: {}", gatewayIpAddress);
            throw new InvalidTransactionException();
        }

        IpnResponse response = sslcommerzUtil.extractIpnResponse(ipnResponse);
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

        if (!matchTransactionParameters(transaction, response)) {
            log.warn("Response parameters don't match for transaction: {}", transaction.getId());
        }

        if (!validateTransaction(response.getValId())) {
            log.warn("Validation failed for transaction: {}", transaction.getId());
            throw new InvalidTransactionException();
        }
    }

    private ValidationResponse sendValidationRequest(String validationId) {
        String validationUrl = sslCommerzConfig.getValidationApiUrl()
                + "?val_id=" + validationId
                + "&store_id=" + sslCommerzConfig.getStoreId()
                + "&store_passwd=" + sslCommerzConfig.getStorePassword()
                + "&v=1&format=json";

        return restClient.get()
                .uri(validationUrl)
                .retrieve()
                .body(ValidationResponse.class);
    }

    @Modifying
    @Transactional
    private boolean validateTransaction(String validationId) {
        ValidationResponse response = sendValidationRequest(validationId);
        if (response == null) {
            return false;
        }
        Transaction transaction = getDBTransactionFromResponse(response.getTranId());
        if (!matchTransactionParameters(transaction, response)) {
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

    private boolean hasNullInRequiredParameters(PaymentResponse response) {
        return response.getCurrencyRate() == null ||
            response.getCurrencyAmount() == null ||
            response.getAmount() == null ||
            response.getCurrencyType() == null;
    }

    private boolean matchTransactionParameters(Transaction transaction, PaymentResponse response) {
        if (hasNullInRequiredParameters(response)) {
            log.warn("Null value in required parameters of payment response for transaction : {}", transaction.getId());
            return false;
        }
        try {
            double storeAmount = Double.parseDouble(response.getStoreAmount());
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

    public TransactionQueryDTO initiateTransactionQuery(Long transactionId) {
        Transaction transaction = transactionService.get(transactionId)
                .orElseThrow(() -> new TransactionNotFoundException("Transaction not found"));

        TransactionQueryResponse response = sendTransactionQueryRequest(transaction);
        if (response == null) {
            log.warn("No response for query refund request: {}", transaction.getId());
            throw new NoResponseException("No response");
        }
        if (!"DONE".equals(response.getApiConnect())) {
            log.warn("Api connection status: {} for transaction query request: {}", response.getApiConnect(), transaction.getId());
            throw new ApiConnectionException("Api connection error");
        }
        log.info("Transaction id: {}, status: {}", transaction.getId(), response.getStatus());
        return new TransactionQueryDTO(transaction.getId(), transaction.getBdtAmount(), response.getStatus());
    }

    private TransactionQueryResponse sendTransactionQueryRequest(Transaction transaction) {
        String transactionQueryUrl = sslCommerzConfig.getMerchantTransIdValidationApiUrl()
                + "?sessionkey=" + transaction.getSessionKey()
                + "&store_id=" + sslCommerzConfig.getStoreId()
                + "&store_passwd=" + sslCommerzConfig.getStorePassword();

        return restClient.get()
                .uri(transactionQueryUrl)
                .retrieve()
                .body(TransactionQueryResponse.class);
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
                String hashedPass = HashUtil.md5(sslCommerzConfig.getStorePassword());
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
                String generatedHash = HashUtil.md5(hashStringBuilder.toString());
                return generatedHash.equals(verifySign);
            }
        }
        return false;
    }

}
