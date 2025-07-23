package com.ekhonni.backend.exception.payment;

/**
 * Author: Asif Iqbal
 * Date: 12/24/24
 */
public class InvalidTransactionException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Invalid transaction";
    }
}
