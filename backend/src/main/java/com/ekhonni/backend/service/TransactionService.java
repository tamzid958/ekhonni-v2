package com.ekhonni.backend.service;

import com.ekhonni.backend.enums.BidStatus;
import com.ekhonni.backend.enums.TransactionStatus;
import com.ekhonni.backend.exception.payment.TransactionNotFoundException;
import com.ekhonni.backend.model.Account;
import com.ekhonni.backend.model.Bid;
import com.ekhonni.backend.model.Transaction;
import com.ekhonni.backend.payment.sslcommerz.PaymentResponse;
import com.ekhonni.backend.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    private final AccountService accountService;

    public TransactionService(TransactionRepository transactionRepository, BidService bidService, AccountService accountService) {
        super(transactionRepository);
        this.transactionRepository = transactionRepository;
        this.accountService = accountService;
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
        transactionRepository.save(transaction);
        return transaction;
    }

    @Modifying
    @Transactional
    public void updateSessionKey(Transaction transaction, String sessionKey) {
        transaction.setSessionKey(sessionKey);
    }

    @Modifying
    @Transactional
    public void updateStatus(Transaction transaction, TransactionStatus status) {
        transaction.setStatus(status);
    }

    @Modifying
    @Transactional
    public void updateValidatedTransaction(Transaction transaction, PaymentResponse response) {
        TransactionStatus status = TransactionStatus.valueOf(response.getStatus());
        if ("1".equals(response.getRiskLevel())) {
            status = TransactionStatus.VALID_WITH_RISK;
        }
        transaction.setStatus(status);
        updateTransaction(transaction, response);
    }

    @Modifying
    @Transactional
    public void updateTransaction(Transaction transaction, PaymentResponse response) {
        transaction.setStoreAmount(Double.parseDouble(response.getAmount()));
        transaction.setBdtAmount(Double.parseDouble(response.getAmount()));
        transaction.setValidationId(response.getValId());
        transaction.setBankTransactionId(response.getBankTranId());
        transaction.setProcessedAt(LocalDateTime.parse(response.getTranDate(),
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        transaction.getBid().setStatus(BidStatus.PAID);

        Account sellerAccount = accountService.getByUserId(transaction.getSeller().getId());
        sellerAccount.setTotalEarnings(sellerAccount.getTotalEarnings() + transaction.getBdtAmount());

        Account superAdminAccount = accountService.getSuperAdminAccount();
        superAdminAccount.setTotalEarnings(superAdminAccount.getTotalEarnings() + transaction.getBdtAmount());
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

}
