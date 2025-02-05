package com.ekhonni.backend.projection.withdraw;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.catalina.Loader;

import java.time.LocalDateTime;

/**
 * Author: Asif Iqbal
 * Date: 12/9/24
 */
public interface WithdrawProjection {
    Long getId();
    String getBankTransactionId();
    Double getAmount();
    String getCurrency();
    LocalDateTime getProcessedAt();
}
