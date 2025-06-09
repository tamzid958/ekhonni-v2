package com.ekhonni.backend.exception.withdraw;

/**
 * Author: Asif Iqbal
 * Date: 2/3/25
 */
public class InsufficientBalanceException extends RuntimeException {
    public InsufficientBalanceException(String message) {
        super(message);
    }
}
