package com.ekhonni.backend.repository;

import com.ekhonni.backend.enums.PayoutCategory;
import com.ekhonni.backend.enums.PayoutMethod;

/**
 * Author: Asif Iqbal
 * Date: 2/2/25
 */
public interface PayoutAccountProjection {
    Long getId();
    Long getAccountId();
    Long getAccountUserId();
    PayoutCategory getCategory();
    PayoutMethod getMethod();
    String getPayoutAccountNumber();
    String getBankName();
    String getBranchName();
    String getAccountHolderName();
    String getRoutingNumber();
}

