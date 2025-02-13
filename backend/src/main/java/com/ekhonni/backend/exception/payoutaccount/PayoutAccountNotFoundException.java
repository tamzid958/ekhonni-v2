package com.ekhonni.backend.exception.payoutaccount;

/**
 * Author: Asif Iqbal
 * Date: 2/2/25
 */
public class PayoutAccountNotFoundException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Payout account not found";
    }
}
