package com.ekhonni.backend.repository;

import com.ekhonni.backend.enums.PayoutCategory;
import com.ekhonni.backend.enums.PayoutMethod;

import java.util.UUID;

/**
 * Author: Asif Iqbal
 * Date: 2/2/25
 */
public interface PayoutAccountProjection {
    Long getId();
    Long getAccountId();
    UUID getAccountUserId();
    PayoutCategory getCategory();
    PayoutMethod getMethod();
    String getPayoutAccountNumber();
    String getBankName();
    String getBranchName();
    String getAccountHolderName();
    String getRoutingNumber();
}

