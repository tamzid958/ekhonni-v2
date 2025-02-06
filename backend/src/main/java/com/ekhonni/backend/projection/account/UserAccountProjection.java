package com.ekhonni.backend.projection.account;

import com.ekhonni.backend.enums.AccountStatus;
import org.springframework.beans.factory.annotation.Value;

/**
 * Author: Asif Iqbal
 * Date: 2/6/25
 */
public interface UserAccountProjection {

    Long getId();
    AccountStatus getStatus();

    @Value("#{target.totalEarnings - target.totalWithdrawals}")
    Double getBalance();

}
