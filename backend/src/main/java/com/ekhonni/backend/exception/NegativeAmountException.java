package com.ekhonni.backend.exception;

/**
 * Author: Asif Iqbal
 * Date: 12/9/24
 */
public class NegativeAmountException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Amount cannot be negative.";
    }
}
