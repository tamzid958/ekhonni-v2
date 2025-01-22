package com.ekhonni.backend.exception.refund;

/**
 * Author: Asif Iqbal
 * Date: 1/22/25
 */
public class NoResponseException extends RuntimeException {
    public NoResponseException(String message) {
        super(message);
    }
}
