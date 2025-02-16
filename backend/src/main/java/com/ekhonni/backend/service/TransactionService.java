package com.ekhonni.backend.service;

import com.ekhonni.backend.enums.TransactionStatus;
import com.ekhonni.backend.exception.payment.TransactionNotFoundException;
import com.ekhonni.backend.model.Bid;
import com.ekhonni.backend.model.Transaction;
import com.ekhonni.backend.projection.transaction.TransactionProjection;
import com.ekhonni.backend.repository.TransactionRepository;
import com.ekhonni.backend.util.AuthUtil;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

/**
 * Author: Asif Iqbal
 * Date: 12/19/24
 */
@Slf4j
@Service
public class TransactionService extends BaseService<Transaction, Long> {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        super(transactionRepository);
        this.transactionRepository = transactionRepository;
    }

    public UUID getSellerId(Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new TransactionNotFoundException("Transaction not found"));
        return transaction.getSeller().getId();
    }

    public UUID getBuyerId(Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new TransactionNotFoundException("Transaction not found"));
        return transaction.getBuyer().getId();
    }

    @Transactional
    public Transaction create(Bid bid) {
        Transaction transaction = new Transaction();
        transaction.setBid(bid);
        transaction.setStatus(TransactionStatus.PENDING);
        return transactionRepository.save(transaction);
    }

    @Transactional
    public void updateSessionKey(Transaction transaction, String sessionKey) {
        transaction.setSessionKey(sessionKey);
    }

    @Transactional
    public void updateStatus(Transaction transaction, TransactionStatus status) {
        transaction.setStatus(status);
    }

    public boolean existsByBidId(Long bidId) {
        return transactionRepository.existsByBidIdAndDeletedAtIsNull(bidId);
    }

    public Optional<Transaction> findByBidId(Long bidId) {
        return transactionRepository.findByBidId(bidId);
    }

    @Transactional
    public void deletePermanentlyByBidId(Long bidId) {
        transactionRepository.deleteByBidId(bidId);
    }

    public Page<TransactionProjection> getAllForAuthenticatedUser(Pageable pageable) {
        UUID userId = AuthUtil.getAuthenticatedUser().getId();
        return transactionRepository.findByDeletedAtIsNullAndBidBidderIdOrBidProductSellerId(
                userId, userId, TransactionProjection.class, pageable);
    }

    public Page<TransactionProjection> getAllByStatus(TransactionStatus status, Pageable pageable) {
        return transactionRepository.findByStatusAndDeletedAtIsNull(status, TransactionProjection.class, pageable);
    }

    public Page<TransactionProjection> getUserTransactionsByStatus(TransactionStatus status, Pageable pageable) {
        UUID userId = AuthUtil.getAuthenticatedUser().getId();
        return transactionRepository.findByBidBidderIdAndStatusAndDeletedAtIsNull(userId, status, TransactionProjection.class, pageable);
    }

    public Page<TransactionProjection> getAllTransactions(Pageable pageable) {
        return transactionRepository.findBy(TransactionProjection.class, pageable);
    }

    public Page<TransactionProjection> getTransactionsByDateRange(
            LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        return transactionRepository.findByCreatedAtBetweenAndDeletedAtIsNull(startDate, endDate, TransactionProjection.class, pageable);
    }

    public Page<TransactionProjection> getAllForUserAdmin(UUID userId, Pageable pageable) {
        return transactionRepository.findByBidBidderIdAndDeletedAtIsNull(userId, TransactionProjection.class, pageable);
    }

    public Page<TransactionProjection> getUserTransactionsByStatusAdmin(UUID userId, TransactionStatus status, Pageable pageable) {
        return transactionRepository.findByBidBidderIdAndStatusAndDeletedAtIsNull(userId, status, TransactionProjection.class, pageable);
    }

    public Page<Transaction> getPendingTransactionsOlderThan(TransactionStatus status, LocalDateTime timestamp, Pageable pageable) {
        return transactionRepository.findByStatusEqualsAndUpdatedAtLessThanEqualAndDeletedAtIsNull(status, timestamp, pageable);
    }
}
