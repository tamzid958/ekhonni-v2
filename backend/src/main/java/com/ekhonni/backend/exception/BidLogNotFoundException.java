package com.ekhonni.backend.exception;

/**
 * Author: Asif Iqbal
 * Date: 12/22/24
 */
public class BidLogNotFoundException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Bid not found.";
    }
}
