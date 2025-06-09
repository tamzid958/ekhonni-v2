package com.ekhonni.backend.exception.bid;

/**
 * Author: Asif Iqbal
 * Date: 1/9/25
 */
public class BidCurrencyMismatchException extends RuntimeException {
    public BidCurrencyMismatchException(String message) {
        super(message);
    }
}
