package com.ekhonni.backend.projection.transaction;

import java.time.LocalDateTime;

/**
 * Author: Asif Iqbal
 * Date: 2/3/25
 */
public interface TransactionProjection {
    Long getId();
    Double getAmount();
    String getCurrency();
    LocalDateTime getProcessedAt();
}
