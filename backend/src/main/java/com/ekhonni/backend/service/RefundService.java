package com.ekhonni.backend.service;

import com.ekhonni.backend.config.SSLCommerzConfig;
import com.ekhonni.backend.model.Refund;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Author: Asif Iqbal
 * Date: 1/14/25
 */
@Service
@AllArgsConstructor
public class RefundService extends BaseService<Refund, Long> {
    TransactionService transactionService;
    SSLCommerzConfig sslCommerzConfig;


    @Transactional
    public Object create(Long transactionId) {

    }
}
