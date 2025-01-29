package com.ekhonni.backend.exception.bid;

/**
 * Author: Asif Iqbal
 * Date: 1/9/25
 */
public class InvalidBidAmountException extends RuntimeException {
    public InvalidBidAmountException(String message) {
        super(message);
    }
}
