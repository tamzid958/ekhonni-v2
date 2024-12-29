package com.ekhonni.backend.service;

import com.ekhonni.backend.enums.BidLogStatus;
import com.ekhonni.backend.enums.TransactionStatus;
import com.ekhonni.backend.exception.BidLogNotFoundException;
import com.ekhonni.backend.exception.InvalidTransactionException;
import com.ekhonni.backend.model.BidLog;
import com.ekhonni.backend.model.Transaction;
import com.ekhonni.backend.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Author: Asif Iqbal
 * Date: 12/19/24
 */
@Slf4j
@Service
public class TransactionService extends BaseService<Transaction, Long> {

    private final TransactionRepository transactionRepository;
    private final BidLogService bidLogService;

    public TransactionService(TransactionRepository transactionRepository, BidLogService bidLogService) {
        super(transactionRepository);
        this.transactionRepository = transactionRepository;
        this.bidLogService = bidLogService;
    }

    @Transactional
    public Transaction create(Long bidLogId) {
        BidLog bidLog = bidLogService.get(bidLogId);
        if (bidLog.getStatus() != BidLogStatus.ACCEPTED) {
            log.warn("Transaction request for unaccepted bid: {}", bidLogId);
            throw new InvalidTransactionException();
        }
        Transaction transaction = new Transaction();
        transaction.setBidLog(bidLog);
        transaction.setStatus(TransactionStatus.PENDING);
        return transactionRepository.save(transaction);
    }

    @Transactional
    public void deletePermanentlyByBidLogId(Long bidLogId) {
        transactionRepository.deleteByBidLogId(bidLogId);
    }

    public boolean existsByBidLogId(Long bidLogId) {
        return transactionRepository.existsByBidLogIdAndDeletedAtIsNull(bidLogId);
    }

}
