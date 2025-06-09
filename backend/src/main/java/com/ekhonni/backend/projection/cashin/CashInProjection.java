package com.ekhonni.backend.projection.cashin;

import com.ekhonni.backend.enums.PaymentMethod;
import com.ekhonni.backend.enums.TransactionStatus;

import java.time.LocalDateTime;

/**
 * Author: Asif Iqbal
 * Date: 2/10/25
 */
public interface CashInProjection {
    Long getId();
    Long getAccountId();
    Double getAmount();
    String getCurrency();
    PaymentMethod getMethod();
    TransactionStatus getStatus();
    String getBankTransactionId();
    LocalDateTime getCreatedAt();
    LocalDateTime getProcessedAt();
}
