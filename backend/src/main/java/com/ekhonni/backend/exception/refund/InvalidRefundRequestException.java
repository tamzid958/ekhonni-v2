package com.ekhonni.backend.exception.refund;

/**
 * Author: Asif Iqbal
 * Date: 12/24/24
 */
public class InvalidRefundRequestException extends RuntimeException {
    public InvalidRefundRequestException(String message) {
        super(message);
    }
}
