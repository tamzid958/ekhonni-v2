package com.ekhonni.backend.projection.account;

/**
 * Author: Asif Iqbal
 * Date: 2/16/25
 */
public interface AccountReportProjection {
    Double getTotalEarnings();
    Double getTotalWithdrawals();
    Double getTotalBalance();
}