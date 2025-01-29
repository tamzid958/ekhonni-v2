package com.ekhonni.backend.exception.payment;

/**
 * Author: Asif Iqbal
 * Date: 1/22/25
 */
public class ApiConnectionException extends RuntimeException {
    public ApiConnectionException(String message) {
        super(message);
    }
}
