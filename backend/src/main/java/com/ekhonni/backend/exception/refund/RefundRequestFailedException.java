package com.ekhonni.backend.exception.refund;

/**
 * Author: Asif Iqbal
 * Date: 1/22/25
 */
public class RefundRequestFailedException extends RuntimeException {
    public RefundRequestFailedException(String message) {
        super(message);
    }
}
