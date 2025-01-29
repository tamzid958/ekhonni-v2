package com.ekhonni.backend.exception.bid;

/**
 * Author: Asif Iqbal
 * Date: 1/27/25
 */
public class BidNotAcceptedException extends RuntimeException {
    public BidNotAcceptedException(String message) {
        super(message);
    }
}
