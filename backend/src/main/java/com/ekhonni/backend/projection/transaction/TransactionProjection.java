package com.ekhonni.backend.projection.transaction;

/**
 * Author: Asif Iqbal
 * Date: 2/3/25
 */
public interface TransactionProjection {
    Long getId();
    Long getAmount();
    Long getProcessedAt();
}
