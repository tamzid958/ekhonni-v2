package com.ekhonni.backend.exception.refund;

/**
 * Author: Asif Iqbal
 * Date: 1/21/25
 */

public class RefundNotFoundException extends RuntimeException {
    public RefundNotFoundException(String message) {
        super(message);
    }
}
