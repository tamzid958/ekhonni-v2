package com.ekhonni.backend.repository;

import com.ekhonni.backend.model.BidLog;
import com.ekhonni.backend.model.Transaction;

/**
 * Author: Asif Iqbal
 * Date: 12/19/24
 */
public interface TransactionRepository extends BaseRepository<Transaction, Long> {
    BidLog findBidLogById(Long id);
    void deleteByBidLogId(Long bidLogId);
}
