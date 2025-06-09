package com.ekhonni.backend.exception.payment;

/**
 * Author: Asif Iqbal
 * Date: 1/30/25
 */
public class InvalidTransactionRequestException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Invalid transaction request";
    }
}
