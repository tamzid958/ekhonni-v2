package com.ekhonni.backend.exception;

/**
 * Author: Asif Iqbal
 * Date: 12/22/24
 */
public class BidNotFoundException extends RuntimeException {
    public BidNotFoundException(String message) {
        super(message);
    }
}
