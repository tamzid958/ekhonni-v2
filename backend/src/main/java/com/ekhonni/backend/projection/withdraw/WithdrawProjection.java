package com.ekhonni.backend.projection.withdraw;

import com.ekhonni.backend.enums.PayoutCategory;
import com.ekhonni.backend.enums.PayoutMethod;
import com.ekhonni.backend.enums.WithdrawStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.catalina.Loader;

import java.time.LocalDateTime;

/**
 * Author: Asif Iqbal
 * Date: 12/9/24
 */
public interface WithdrawProjection {

    Long getId();

    WithdrawStatus getStatus();

    Long getPayoutAccountId();

    @JsonProperty("payoutAccountNumber")
    String getPayoutAccountPayoutAccountNumber();

    @JsonProperty("payoutCategory")
    PayoutCategory getPayoutAccountCategory();

    @JsonProperty("payoutMethod")
    PayoutMethod getPayoutAccountMethod();

    String getBankTransactionId();

    Double getAmount();

    String getCurrency();

    LocalDateTime getCreatedAt();
}
