package com.ekhonni.backend.service;

import com.ekhonni.backend.enums.BidLogStatus;
import com.ekhonni.backend.enums.TransactionStatus;
import com.ekhonni.backend.exception.BidLogNotFoundException;
import com.ekhonni.backend.model.BidLog;
import com.ekhonni.backend.model.Transaction;
import com.ekhonni.backend.repository.BidLogRepository;
import com.ekhonni.backend.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Author: Asif Iqbal
 * Date: 12/19/24
 */

@Service
public class TransactionService extends BaseService<Transaction, Long> {

    private final TransactionRepository transactionRepository;
    private final BidLogRepository bidLogRepository;

    public TransactionService(TransactionRepository transactionRepository, BidLogRepository bidLogRepository) {
        super(transactionRepository);
        this.transactionRepository = transactionRepository;
        this.bidLogRepository = bidLogRepository;
    }

    @Transactional
    public Transaction create(Long bidLogId) {
        BidLog bidLog = bidLogRepository.findById(bidLogId)
                .orElseThrow(BidLogNotFoundException::new);

        if (bidLog.getStatus() != BidLogStatus.ACCEPTED) {
            throw new RuntimeException("Bid is not accepted");
        }

        Transaction transaction = new Transaction();
        transaction.setBidLog(bidLog);
        transaction.setStatus(TransactionStatus.PENDING);
        return transactionRepository.save(transaction);
    }

}
