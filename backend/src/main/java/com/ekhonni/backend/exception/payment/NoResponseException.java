package com.ekhonni.backend.exception.payment;

/**
 * Author: Asif Iqbal
 * Date: 1/22/25
 */
public class NoResponseException extends RuntimeException {
    public NoResponseException(String message) {
        super(message);
    }
}
