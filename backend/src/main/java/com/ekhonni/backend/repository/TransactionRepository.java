package com.ekhonni.backend.repository;

import com.ekhonni.backend.model.Bid;
import com.ekhonni.backend.model.Transaction;

/**
 * Author: Asif Iqbal
 * Date: 12/19/24
 */
public interface TransactionRepository extends BaseRepository<Transaction, Long> {
    Bid findBidById(Long id);
}
