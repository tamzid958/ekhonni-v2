package com.ekhonni.backend.service;

import com.ekhonni.backend.enums.BidStatus;
import com.ekhonni.backend.enums.TransactionStatus;
import com.ekhonni.backend.exception.EntityNotFoundException;
import com.ekhonni.backend.exception.InvalidTransactionException;
import com.ekhonni.backend.model.Bid;
import com.ekhonni.backend.model.Transaction;
import com.ekhonni.backend.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
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
    private final BidService bidService;

    public TransactionService(TransactionRepository transactionRepository, BidService bidService) {
        super(transactionRepository);
        this.transactionRepository = transactionRepository;
        this.bidService = bidService;
    }

    @Transactional
    public Transaction create(Long bidId) {
        Bid bid = bidService.get(bidId)
                .orElseThrow(InvalidTransactionException::new);
        if (bid.getStatus() != BidStatus.ACCEPTED) {
            log.warn("Transaction request for unaccepted bid: {}", bidId);
            throw new InvalidTransactionException();
        }
        Transaction transaction = new Transaction();
        transaction.setBid(bid);
        transaction.setStatus(TransactionStatus.PENDING);
        return transactionRepository.save(transaction);
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
