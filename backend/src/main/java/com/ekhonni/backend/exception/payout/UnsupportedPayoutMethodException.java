package com.ekhonni.backend.exception.payout;

/**
 * Author: Asif Iqbal
 * Date: 2/5/25
 */
public class UnsupportedPayoutMethodException extends RuntimeException {
    public UnsupportedPayoutMethodException(String message) {
        super(message);
    }
}
