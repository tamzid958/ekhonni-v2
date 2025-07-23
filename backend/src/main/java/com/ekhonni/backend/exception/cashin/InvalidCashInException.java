package com.ekhonni.backend.exception.cashin;

/**
 * Author: Asif Iqbal
 * Date: 2/11/25
 */
public class InvalidCashInException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Invalid cash in";
    }
}
