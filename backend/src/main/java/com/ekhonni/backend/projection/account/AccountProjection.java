package com.ekhonni.backend.projection.account;

import com.ekhonni.backend.enums.AccountStatus;

/**
 * Author: Asif Iqbal
 * Date: 12/9/24
 */

public interface AccountProjection {
    Long getId();
    Double getTotalEarnings();
    Double getTotalWithdrawals();
    AccountStatus getStatus();

    default Double getBalance() {
        return getTotalEarnings() - getTotalWithdrawals();
    }
}

