package com.ekhonni.backend.service;

import com.ekhonni.backend.enums.TransactionStatus;
import com.ekhonni.backend.model.Bid;
import com.ekhonni.backend.model.Transaction;
import com.ekhonni.backend.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Author: Asif Iqbal
 * Date: 12/19/24
 */
@Slf4j
@Service
public class TransactionService extends BaseService<Transaction, Long> {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository, BidService bidService) {
        super(transactionRepository);
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    public Transaction create(Bid bid) {
        Transaction transaction = new Transaction();
        transaction.setBid(bid);
        transaction.setStatus(TransactionStatus.PENDING);
        return transactionRepository.save(transaction);
    }

    @Modifying
    @Transactional
    public void updateSessionKey(Transaction transaction, String sessionKey) {
        transaction.setSessionKey(sessionKey);
    }

    @Transactional
    public void deletePermanentlyByBidId(Long bidId) {
        transactionRepository.deleteByBidId(bidId);
    }

    public boolean existsByBidId(Long bidId) {
        return transactionRepository.existsByBidIdAndDeletedAtIsNull(bidId);
    }

    public Optional<Transaction> findByBidId(Long bidId) {
        return transactionRepository.findByBidId(bidId);
    }

}
