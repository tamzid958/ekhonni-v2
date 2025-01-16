package com.ekhonni.backend.service;

import com.ekhonni.backend.config.SSLCommerzConfig;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Author: Asif Iqbal
 * Date: 1/14/25
 */
@Service
@AllArgsConstructor
public class RefundService {
    TransactionService transactionService;
    SSLCommerzConfig sslCommerzConfig;

    
}
