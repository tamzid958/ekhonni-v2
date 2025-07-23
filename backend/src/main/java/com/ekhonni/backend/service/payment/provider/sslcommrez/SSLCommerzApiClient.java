package com.ekhonni.backend.service.payment.provider.sslcommrez;

import com.ekhonni.backend.config.payment.SSLCommerzConfig;
import com.ekhonni.backend.enums.BidStatus;
import com.ekhonni.backend.enums.PaymentMethod;
import com.ekhonni.backend.enums.TransactionStatus;
import com.ekhonni.backend.exception.payment.*;
import com.ekhonni.backend.model.Account;
import com.ekhonni.backend.model.Bid;
import com.ekhonni.backend.model.CashIn;
import com.ekhonni.backend.model.Transaction;
import com.ekhonni.backend.service.AccountService;
import com.ekhonni.backend.service.BidService;
import com.ekhonni.backend.service.CashInService;
import com.ekhonni.backend.service.TransactionService;
import com.ekhonni.backend.service.payment.provider.sslcommrez.response.*;
import com.ekhonni.backend.util.HashUtil;
import com.ekhonni.backend.util.HttpUtil;
import com.ekhonni.backend.util.SslcommerzUtil;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Author: Asif Iqbal
 * Date: 2/10/25
 */
@Component
@AllArgsConstructor
@Slf4j
public class SSLCommerzApiClient {

    private final TransactionService transactionService;
    private final CashInService cashInService;
    private final AccountService accountService;
    private final BidService bidService;
    private final SslcommerzUtil sslcommerzUtil;
    private final SSLCommerzConfig sslCommerzConfig;
    private final RestClient restClient;
    private static final double CURRENCY_CONVERSION_TOLERANCE = 0.01;


    @Retry(name = "retryPayment", fallbackMethod = "initiatePaymentFallback")
    public InitiatePaymentResponse initiatePayment(Long bidId) throws Exception {
        Bid bid = bidService.get(bidId).orElseThrow(() -> {
                    log.warn("Payment request for invalid bid: {}", bidId);
                    return new InvalidTransactionRequestException();
                });

        verifyBid(bid);

        Transaction transaction = transactionService.findByBidId(bidId)
                .orElseGet(() -> transactionService.create(bid, PaymentMethod.SSLCOMMERZ));

        if (EnumSet.of(TransactionStatus.VALID, TransactionStatus.VALIDATED, TransactionStatus.VALID_WITH_RISK)
                .contains(transaction.getStatus())) {
            log.warn("Transaction already processed: {}", transaction.getId());
            throw new InvalidTransactionRequestException();
        }

        transactionService.updateMethod(transaction, PaymentMethod.SSLCOMMERZ);

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

    private String prepareRequestBody(Transaction transaction) {
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

    @Transactional
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

        if (EnumSet.of(TransactionStatus.VALID, TransactionStatus.VALIDATED, TransactionStatus.VALID_WITH_RISK)
                .contains(transaction.getStatus())) {
            log.warn("Transaction already processed: {}", transaction.getId());
            return;
        }

        if (!ipnHashVerify(ipnResponse)) {
            transactionService.updateStatus(transaction, TransactionStatus.SIGNATURE_MISMATCH);
            log.warn("IPN signature verification failed for transaction : {}", response.getTranId());
            throw new InvalidTransactionException();
        }

        String status = response.getStatus() == null ? "PROCESSING" : response.getStatus();
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

    @Transactional
    private boolean validateTransaction(String validationId) {
        ValidationResponse response = sendValidationRequest(validationId);
        if (response == null) {
            return false;
        }
        Transaction transaction = getDBTransactionFromResponse(response.getTranId());
        if (!matchTransactionParameters(transaction, response)) {
            transactionService.updateStatus(transaction, TransactionStatus.PARAMETERS_MISMATCH);
            updateTransaction(transaction, response);
            log.warn("Validation response parameters don't match for transaction: {}", response.getTranId());
            return false;
        }
        updateValidatedTransaction(transaction, response);
        return true;
    }

    @Transactional
    public void updateValidatedTransaction(Transaction transaction, PaymentResponse response) {
        TransactionStatus status = TransactionStatus.valueOf(response.getStatus());
        if ("1".equals(response.getRiskLevel())) {
            status = TransactionStatus.VALID_WITH_RISK;
        }
        transaction.setStatus(status);
        updateTransaction(transaction, response);
    }

    @Transactional
    public void updateTransaction(Transaction transaction, PaymentResponse response) {
        transaction.setStoreAmount(response.getStoreAmount());
        transaction.setBdtAmount(response.getAmount());
        transaction.setValidationId(response.getValId());
        transaction.setBankTransactionId(response.getBankTranId());
        transaction.setProcessedAt(LocalDateTime.parse(response.getTranDate(),
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        transaction.getBid().setStatus(BidStatus.PAID);

        Account sellerAccount = accountService.getByUserId(transaction.getSeller().getId());
        accountService.deposit(sellerAccount, transaction.getBdtAmount());
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
        double expectedBdtAmount = transaction.getAmount() * response.getCurrencyRate();
        return response.getCurrencyType().equals(transaction.getCurrency())
                && Objects.equals(response.getCurrencyAmount(), transaction.getAmount())
                && (Math.abs(expectedBdtAmount - response.getAmount()) <= CURRENCY_CONVERSION_TOLERANCE);
    }

    @Scheduled(fixedDelay = 1800000)
    public void checkPendingTransactions() {
        log.info("Starting processing of pending transactions");
        LocalDateTime timestamp = LocalDateTime.now().minusMinutes(25);

        final int BATCH_SIZE = 100;
        int pageNumber = 0;
        boolean hasMorePages = true;
        Sort sort = Sort.by("id").ascending();
        while (hasMorePages) {
            PageRequest pageRequest = PageRequest.of(pageNumber, BATCH_SIZE, sort);
            Page<Transaction> transactionPage = transactionService
                    .getPendingTransactionsOlderThan(TransactionStatus.PENDING, timestamp, pageRequest);
            processPendingTransactions(transactionPage.getContent());
            hasMorePages = transactionPage.hasNext();
            pageNumber++;
        }
    }

    private void processPendingTransactions(List<Transaction> transactions) {
        for (Transaction transaction : transactions) {
            try {
                TransactionQueryResponse response = initiateTransactionQuery(transaction.getSessionKey());
                log.info("Transaction id: {}, status: {}", transaction.getId(), response.getStatus());

                String status = response.getStatus() == null ? "PENDING" : response.getStatus();
                transactionService.updateStatus(transaction, TransactionStatus.valueOf(status));
                if ("VALID".equals(status) || "VALIDATED".equals(status)) {
                    updateValidatedTransaction(transaction, response);
                }
            } catch (Exception e) {
                log.info(e.getMessage());
            }
        }
    }

    public TransactionQueryResponse initiateTransactionQuery(String sessionKey) {
        TransactionQueryResponse response = sendTransactionQueryRequest(sessionKey);
        if (response == null) {
            throw new NoResponseException("No response");
        }
        if (!"DONE".equals(response.getApiConnect())) {
            throw new ApiConnectionException("Api connection error");
        }
        return response;
    }

    private TransactionQueryResponse sendTransactionQueryRequest(String sessionKey) {
        String transactionQueryUrl = sslCommerzConfig.getMerchantTransIdValidationApiUrl()
                + "?sessionkey=" + sessionKey
                + "&store_id=" + sslCommerzConfig.getStoreId()
                + "&store_passwd=" + sslCommerzConfig.getStorePassword();

        return restClient.get()
                .uri(transactionQueryUrl)
                .retrieve()
                .body(TransactionQueryResponse.class);
    }

    public InitiatePaymentResponse initiateCashIn(double amount) {
        CashIn cashIn = cashInService.create(amount, PaymentMethod.SSLCOMMERZ);
        String requestBody = prepareCashInRequestBody(cashIn);
        InitialResponse response = sendPaymentRequest(requestBody);
        verifyInitialResponse(response);

        cashInService.updateSessionKey(cashIn, response.getSessionkey());
        return new InitiatePaymentResponse(response.getGatewayPageURL());
    }

    private String prepareCashInRequestBody(CashIn cashIn) {
        return sslcommerzUtil.getParamsString(cashIn, true);
    }

    @Transactional
    public void verifyCashIn(Map<String, String> ipnResponse, HttpServletRequest request) {

        String gatewayIpAddress = HttpUtil.getIpAddress(request);
        if (!sslCommerzConfig.getAllowedIps().contains(gatewayIpAddress)) {
            log.warn("CashIn ipn from unknown ip: {}", gatewayIpAddress);
            throw new InvalidTransactionException();
        }

        IpnResponse response = sslcommerzUtil.extractIpnResponse(ipnResponse);
        CashIn cashIn = getDBCashInFromResponse(response.getTranId());

        if (EnumSet.of(TransactionStatus.VALID, TransactionStatus.VALIDATED, TransactionStatus.VALID_WITH_RISK)
                .contains(cashIn.getStatus())) {
            log.warn("Cash in already processed: {}", cashIn.getId());
            return;
        }

        if (!ipnHashVerify(ipnResponse)) {
            cashInService.updateStatus(cashIn, TransactionStatus.SIGNATURE_MISMATCH);
            log.warn("IPN signature verification failed for CashIn : {}", response.getTranId());
            throw new InvalidTransactionException();
        }

        String status = response.getStatus() == null ? "PENDING" : response.getStatus();
        if (!("VALID".equals(status) || "VALIDATED".equals(status))) {
            cashInService.updateStatus(cashIn, TransactionStatus.valueOf(status));
            log.warn("Unsuccessful CashIn: {}, status: {}", cashIn.getId(), status);
            return;
        }

        if (!matchCashInParameters(cashIn, response)) {
            log.warn("Response parameters don't match for CashIn: {}", cashIn.getId());
        }

        if (!validateCashIn(response.getValId())) {
            log.warn("Validation failed for CashIn: {}", cashIn.getId());
            throw new InvalidTransactionException();
        }
    }

    @Transactional
    private boolean validateCashIn(String validationId) {
        ValidationResponse response = sendValidationRequest(validationId);
        if (response == null) {
            return false;
        }
        CashIn cashIn = getDBCashInFromResponse(response.getTranId());
        if (!matchCashInParameters(cashIn, response)) {
            cashInService.updateStatus(cashIn, TransactionStatus.PARAMETERS_MISMATCH);
            updateCashIn(cashIn, response);
            log.warn("Validation response parameters don't match for CashIn: {}", response.getTranId());
            return false;
        }
        updateValidatedCashIn(cashIn, response);
        return true;
    }

    @Transactional
    public void updateValidatedCashIn(CashIn cashIn, PaymentResponse response) {
        TransactionStatus status = TransactionStatus.valueOf(response.getStatus());
        if ("1".equals(response.getRiskLevel())) {
            status = TransactionStatus.VALID_WITH_RISK;
        }
        cashIn.setStatus(status);
        updateCashIn(cashIn, response);
    }

    @Transactional
    public void updateCashIn(CashIn cashIn, PaymentResponse response) {
        cashIn.setStoreAmount(response.getStoreAmount());
        cashIn.setBdtAmount(response.getAmount());
        cashIn.setValidationId(response.getValId());
        cashIn.setBankTransactionId(response.getBankTranId());
        cashIn.setProcessedAt(LocalDateTime.parse(response.getTranDate(),
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        Account userAccount = cashIn.getAccount();
        accountService.deposit(userAccount, cashIn.getBdtAmount());
    }

    private CashIn getDBCashInFromResponse(String trxId) {
        long cashInId;
        try {
            cashInId = Long.parseLong(trxId);
        } catch (NumberFormatException e) {
            log.error(e.getMessage());
            throw new InvalidTransactionException();
        }
        return cashInService.get(cashInId)
                .orElseThrow(InvalidTransactionException::new);
    }

    private boolean matchCashInParameters(CashIn cashIn, PaymentResponse response) {
        if (hasNullInRequiredParameters(response)) {
            log.warn("Null value in required parameters of payment response for CashIn : {}", cashIn.getId());
            return false;
        }
        double expectedBdtAmount = cashIn.getAmount() * response.getCurrencyRate();
        return response.getCurrencyType().equals(cashIn.getCurrency())
                && Objects.equals(response.getCurrencyAmount(), cashIn.getAmount())
                && (Math.abs(expectedBdtAmount - response.getAmount()) <= CURRENCY_CONVERSION_TOLERANCE);
    }

    @Scheduled(fixedDelay = 1800000)
    public void checkPendingCashIns() {
        log.info("Starting processing of pending cash-ins");
        LocalDateTime timestamp = LocalDateTime.now().minusMinutes(25);

        final int BATCH_SIZE = 100;
        int pageNumber = 0;
        boolean hasMorePages = true;
        Sort sort = Sort.by("id").ascending();
        while (hasMorePages) {
            PageRequest pageRequest = PageRequest.of(pageNumber, BATCH_SIZE, sort);
            Page<CashIn> cashInPage = cashInService
                    .getPendingCashInsOlderThan(TransactionStatus.PENDING, timestamp, pageRequest);
            processPendingCashIns(cashInPage.getContent());
            hasMorePages = cashInPage.hasNext();
            pageNumber++;
        }
    }

    private void processPendingCashIns(List<CashIn> cashIns) {
        for (CashIn cashIn : cashIns) {
            try {
                TransactionQueryResponse response = initiateTransactionQuery(cashIn.getSessionKey());
                String status = response.getStatus() == null ? "PENDING" : response.getStatus();

                log.info("Cash in id: {}, status: {}", cashIn.getId(), response.getStatus());
                cashInService.updateStatus(cashIn, TransactionStatus.valueOf(status));
                if ("VALID".equals(status) || "VALIDATED".equals(status)) {
                    updateValidatedCashIn(cashIn, response);
                }
            } catch (Exception e) {
                log.info(e.getMessage());
            }
        }
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
