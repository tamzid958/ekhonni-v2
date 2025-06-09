package com.ekhonni.backend.exception.cashin;

/**
 * Author: Asif Iqbal
 * Date: 2/10/25
 */
public class CashInNotFoundException extends RuntimeException {
    public CashInNotFoundException(String message) {
        super(message);
    }
}
