package com.ekhonni.backend.repository;

import com.ekhonni.backend.model.Bid;
import com.ekhonni.backend.model.Transaction;

import java.util.Optional;

/**
 * Author: Asif Iqbal
 * Date: 12/19/24
 */
public interface TransactionRepository extends BaseRepository<Transaction, Long> {
    Optional<Bid> findBidById(Long bidId);
    Optional<Transaction> findByBidId(Long bidId);
    void deleteByBidId(Long bidId);
    boolean existsByBidIdAndDeletedAtIsNull(Long bidId);
}
