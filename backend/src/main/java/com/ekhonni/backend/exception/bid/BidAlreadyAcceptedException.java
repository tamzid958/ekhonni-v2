package com.ekhonni.backend.exception.bid;

/**
 * Author: Asif Iqbal
 * Date: 1/9/25
 */
public class BidAlreadyAcceptedException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Product already has accepted bid";
    }
}
