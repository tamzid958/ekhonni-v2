package com.ekhonni.backend.exception;

import lombok.NoArgsConstructor;

/**
 * Author: Asif Iqbal
 * Date: 12/26/24
 */
@NoArgsConstructor
public class InitiatePaymentException extends RuntimeException {
    public InitiatePaymentException(String message) {
        super(message);
    }
}
