package com.ekhonni.backend.service;
import com.ekhonni.backend.model.Transaction;
import com.ekhonni.backend.repository.BidLogRepository;
import com.ekhonni.backend.repository.BidRepository;
import com.ekhonni.backend.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Author: Asif Iqbal
 * Date: 12/19/24
 */
@Service
@AllArgsConstructor
public class TransactionService extends BaseService<Transaction, Long> {
    private final TransactionRepository transactionRepository;
    private final BidLogRepository bidLogRepository;
    private final BidRepository bidRepository;
    public void create(Long BidLogId) {

    }
}
