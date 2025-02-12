package com.ekhonni.backend.service.payment.provider.sslcommrez;

import com.ekhonni.backend.config.payment.SSLCommerzConfig;
import com.ekhonni.backend.dto.refund.RefundApproveDTO;
import com.ekhonni.backend.dto.refund.RefundRequestDTO;
import com.ekhonni.backend.enums.RefundStatus;
import com.ekhonni.backend.enums.TransactionStatus;
import com.ekhonni.backend.exception.payment.ApiConnectionException;
import com.ekhonni.backend.exception.payment.NoResponseException;
import com.ekhonni.backend.exception.payment.TransactionNotFoundException;
import com.ekhonni.backend.exception.refund.*;
import com.ekhonni.backend.model.Account;
import com.ekhonni.backend.model.Refund;
import com.ekhonni.backend.model.Transaction;
import com.ekhonni.backend.service.AccountService;
import com.ekhonni.backend.service.BaseService;
import com.ekhonni.backend.service.TransactionService;
import com.ekhonni.backend.service.payment.provider.sslcommrez.refund.RefundQueryResponse;
import com.ekhonni.backend.service.payment.provider.sslcommrez.refund.RefundResponse;
import com.ekhonni.backend.repository.RefundRepository;
import com.ekhonni.backend.util.AuthUtil;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Author: Asif Iqbal
 * Date: 1/14/25
 */

@Slf4j
@Service
public class RefundService extends BaseService<Refund, Long> {

    private final RefundRepository refundRepository;
    private final TransactionService transactionService;
    private final AccountService accountService;
    private final SSLCommerzConfig sslCommerzConfig;
    private final RestClient restClient;

    private final int BATCH_SIZE = 50;

    public RefundService(RefundRepository refundRepository,
                         TransactionService transactionService,
                         AccountService accountService,
                         SSLCommerzConfig sslCommerzConfig,
                         RestClient restClient) {
        super(refundRepository);
        this.refundRepository = refundRepository;
        this.transactionService = transactionService;
        this.accountService = accountService;
        this.sslCommerzConfig = sslCommerzConfig;
        this.restClient = restClient;
    }

    @Transactional
    public void create(Long transactionId, RefundRequestDTO refundRequestDTO) {
        if (refundRepository.existsByTransactionId(transactionId)) {
            throw new InvalidRefundRequestException("Refund request already submitted");
        }
        Transaction transaction = transactionService.get(transactionId)
                .orElseThrow(() -> new TransactionNotFoundException("No transaction found for refund request"));
        TransactionStatus status = transaction.getStatus();
        if (!(TransactionStatus.VALID.equals(status) || TransactionStatus.VALIDATED.equals(status))) {
            throw new InvalidRefundRequestException("Transaction not eligible for refund");
        }
        Refund refund = new Refund();
        refund.setTransaction(transaction);
        refund.setRemarks(refundRequestDTO.remarks());
        refundRepository.save(refund);
    }

    @Transactional
    public void approve(Long id, RefundApproveDTO refundApproveDTO) {
        Refund refund = refundRepository.findById(id)
                .orElseThrow(() -> new RefundNotFoundException("Refund not found"));
        refund.setAmount(refundApproveDTO.amount());
        refund.setApprovedBy(AuthUtil.getAuthenticatedUser());
    }

    @Transactional
    private void updateSuccessfulRefund(Refund refund, RefundQueryResponse response) {
        Account sellerAccount = accountService.getByUserId(refund.getTransaction().getSeller().getId());
        Account superAdminAccount = accountService.getSuperAdminAccount();

        sellerAccount.setTotalEarnings(sellerAccount.getTotalEarnings() - refund.getAmount());
        superAdminAccount.setTotalEarnings(superAdminAccount.getTotalEarnings() - refund.getAmount());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        refund.setInitiatedOn(LocalDateTime.parse(response.getInitiatedOn(), formatter));
        refund.setRefundedOn(LocalDateTime.parse(response.getRefundedOn(), formatter));
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void processRefund() {
        log.info("Starting refund processing");
        List<RefundStatus> toBeProcessedStatuses = List.of(
                RefundStatus.PENDING,
                RefundStatus.NO_RESPONSE,
                RefundStatus.API_CONNECTION_FAILED,
                RefundStatus.FAILED
        );

        int pageNumber = 0;
        boolean hasMorePages = true;
        Sort sort = Sort.by("createdAt").ascending()
                .and(Sort.by("id").ascending());

        while (hasMorePages) {
            PageRequest pageRequest = PageRequest.of(pageNumber, BATCH_SIZE, sort);
            Page<Refund> refundPage = refundRepository.findByStatusIn(toBeProcessedStatuses, pageRequest);
            processBatch(refundPage.getContent());
            hasMorePages = refundPage.hasNext();
            pageNumber++;
        }
    }

    private void processBatch(List<Refund> refunds) {
        for (Refund refund : refunds) {
            initiateRefundRequest(refund);
        }
    }

    @Transactional
    private void initiateRefundRequest(Refund refund) {
        RefundResponse response = sendRefundRequest(refund);
        if (response == null) {
            refund.setStatus(RefundStatus.NO_RESPONSE);
            log.warn("No response from gateway for refund: {}", refund.getId());
            throw new NoResponseException("No response");
        }
        if (!"DONE".equals(response.getApiConnect())) {
            refund.setStatus(RefundStatus.API_CONNECTION_FAILED);
            log.warn("Api connection status: {} for refund: {}", response.getApiConnect(), refund.getId());
            throw new ApiConnectionException("Api connection error");
        }

        refund.setStatus(RefundStatus.valueOf(response.getStatus().toUpperCase()));
        if (RefundStatus.FAILED.equals(refund.getStatus())) {
            log.warn("Refund request failed for refund: {}, reason: {}", refund.getId(), response.getErrorReason());
            throw new RefundRequestFailedException("Refund request failed");
        }
        refund.setRefundTransactionId(response.getTransId());
        refund.setBankTransactionId(response.getBankTranId());
        refund.setRefundReferenceId(response.getRefundRefId());
    }

    private RefundResponse sendRefundRequest(Refund refund) {
        String refundUrl = sslCommerzConfig.getMerchantTransIdValidationApiUrl()
                + "?bank_trans_id=" + refund.getTransaction().getBankTransactionId()
                + "&store_id=" + sslCommerzConfig.getStoreId()
                + "&store_passwd=" + sslCommerzConfig.getStorePassword()
                + "&refund_amount=" + refund.getAmount()
                + "&refund_remarks=" + refund.getRemarks()
                + "&refe_id=" + refund.getId()
                + "&format=json";

        return restClient.get()
                .uri(refundUrl)
                .retrieve()
                .body(RefundResponse.class);
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void queryRefund() {
        log.info("Starting refund query");
        List<RefundStatus> toBeQueriedStatuses = List.of(
                RefundStatus.SUCCESS,
                RefundStatus.PROCESSING
        );

        int pageNumber = 0;
        boolean hasMorePages = true;
        Sort sort = Sort.by("createdAt").ascending()
                .and(Sort.by("id").ascending());

        while (hasMorePages) {
            PageRequest pageRequest = PageRequest.of(pageNumber, BATCH_SIZE, sort);
            Page<Refund> refundPage = refundRepository.findByStatusIn(toBeQueriedStatuses, pageRequest);
            processBatch(refundPage.getContent());
            hasMorePages = refundPage.hasNext();
            pageNumber++;
        }
    }

    private void queryBatch(List<Refund> refunds) {
        for (Refund refund : refunds) {
            initiateQueryRefundRequest(refund);
        }
    }

    @Transactional
    private void initiateQueryRefundRequest(Refund refund) {
        RefundQueryResponse response = sendQueryRefundRequest(refund);
        if (response == null) {
            log.warn("No response for query refund request: {}", refund.getId());
            throw new NoResponseException("No response");
        }
        if (!"DONE".equals(response.getApiConnect())) {
            log.warn("Api connection status: {} for refund query request: {}", response.getApiConnect(), refund.getId());
            throw new ApiConnectionException("Api connection error");
        }

        refund.setStatus(RefundStatus.valueOf(response.getStatus().toUpperCase()));
        log.info("Refund id: {}, status: {}", refund.getId(), refund.getStatus());

        if (RefundStatus.REFUNDED.equals(refund.getStatus())) {
            updateSuccessfulRefund(refund, response);
        }
    }

    private RefundQueryResponse sendQueryRefundRequest(Refund refund) {
        String refundUrl = sslCommerzConfig.getMerchantTransIdValidationApiUrl()
                + "?refund_ref_id=" + refund.getRefundReferenceId()
                + "&store_id=" + sslCommerzConfig.getStoreId()
                + "&store_passwd=" + sslCommerzConfig.getStorePassword();

        return restClient.get()
                .uri(refundUrl)
                .retrieve()
                .body(RefundQueryResponse.class);
    }

}
