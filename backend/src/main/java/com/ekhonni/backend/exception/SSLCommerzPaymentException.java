package com.ekhonni.backend.exception;

import lombok.NoArgsConstructor;

/**
 * Author: Asif Iqbal
 * Date: 12/26/24
 */
@NoArgsConstructor
public class SSLCommerzPaymentException extends RuntimeException {
    public SSLCommerzPaymentException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return "Error processing payment";
    }
}
