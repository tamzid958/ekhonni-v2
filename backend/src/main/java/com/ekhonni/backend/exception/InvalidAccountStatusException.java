package com.ekhonni.backend.exception;

/**
 * Author: Asif Iqbal
 * Date: 12/9/24
 */
public class InvalidAccountStatusException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Invalid account status.";
    }
}
