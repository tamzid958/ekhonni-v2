package com.ekhonni.backend.service;

import com.ekhonni.backend.config.SSLCommerzConfig;
import com.ekhonni.backend.dto.refund.RefundApproveDTO;
import com.ekhonni.backend.dto.refund.RefundRequestDTO;
import com.ekhonni.backend.enums.RefundStatus;
import com.ekhonni.backend.enums.TransactionStatus;
import com.ekhonni.backend.exception.payment.TransactionNotFoundException;
import com.ekhonni.backend.exception.refund.InvalidRefundRequestException;
import com.ekhonni.backend.exception.refund.RefundNotFoundException;
import com.ekhonni.backend.model.Refund;
import com.ekhonni.backend.model.Transaction;
import com.ekhonni.backend.payment.sslcommerz.refund.RefundResponse;
import com.ekhonni.backend.repository.RefundRepository;
import com.ekhonni.backend.util.AuthUtil;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Author: Asif Iqbal
 * Date: 1/14/25
 */

@Slf4j
@Service
@AllArgsConstructor
public class RefundService extends BaseService<Refund, Long> {

    private final RefundRepository refundRepository;
    private final TransactionService transactionService;
    private final SSLCommerzConfig sslCommerzConfig;
    private final RestClient restClient;
    private final List<RefundStatus> toBeProcessedStatuses = List.of(
            RefundStatus.PENDING,
            RefundStatus.NO_RESPONSE,
            RefundStatus.API_CONNECTION_FAILED,
            RefundStatus.FAILED
    );
    @Value("${refund.batch-size}")
    private int batchSize;

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

    @Modifying
    @Transactional
    public void approve(Long id, RefundApproveDTO refundApproveDTO) {
        Refund refund = refundRepository.findById(id)
                .orElseThrow(() -> new RefundNotFoundException("Refund not found"));
        refund.setAmount(refundApproveDTO.amount());
        refund.setApprovedBy(AuthUtil.getAuthenticatedUser());
    }

    @Scheduled(fixedDelayString = "${refund.processing-interval}")
    private void processRefund() {
        log.info("Starting refund processing");
        int pageNumber = 0;
        boolean hasMore = true;

        while (hasMore) {
            Sort sort = Sort.by("createdAt").ascending()
                    .and(Sort.by("id").ascending());
            PageRequest pageRequest = PageRequest.of(pageNumber, batchSize, sort);
            Page<Refund> refundPage = refundRepository.findByStatusIn(toBeProcessedStatuses, pageRequest);
            processBatch(refundPage.getContent());
            hasMore = refundPage.hasNext();
            pageNumber++;
        }
    }

    private void processBatch(List<Refund> refunds) {
        for (Refund refund : refunds) {
            initiateRefundRequest(refund);
        }
    }

    @Modifying
    @Transactional
    private void initiateRefundRequest(Refund refund) {
        RefundResponse response = sendRefundRequest(refund);
        if (response == null) {
            refund.setStatus(RefundStatus.NO_RESPONSE);
            log.warn("No response from gateway for refund: {}", refund.getId());
            return;
        }
        if (!"DONE".equals(response.getAPIConnect())) {
            refund.setStatus(RefundStatus.API_CONNECTION_FAILED);
            log.warn("Api connection status: {} for refund: {}", response.getAPIConnect(), refund.getId());
            return;
        }

        refund.setStatus(RefundStatus.valueOf(response.getStatus().toUpperCase()));
        if (RefundStatus.FAILED.equals(refund.getStatus())) {
            log.warn("Refund request failed for refund: {}", refund.getId());
        }

        // Refund request to be retried: NO_RESPONSE, API_CONNECTION_FAILED, FAILED, PENDING
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

}
