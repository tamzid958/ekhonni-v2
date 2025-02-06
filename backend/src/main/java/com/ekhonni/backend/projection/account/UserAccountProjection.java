package com.ekhonni.backend.projection.account;

import org.springframework.beans.factory.annotation.Value;

/**
 * Author: Asif Iqbal
 * Date: 2/6/25
 */
public interface UserAccountProjection {

    Long getId();

    @Value("#{target.totalEarnings - target.totalWithdrawals}")
    Double getBalance();

}
