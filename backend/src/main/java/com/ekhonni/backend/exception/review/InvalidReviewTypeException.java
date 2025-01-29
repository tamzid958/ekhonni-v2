package com.ekhonni.backend.exception.review;

/**
 * Author: Asif Iqbal
 * Date: 1/28/25
 */
public class InvalidReviewTypeException extends RuntimeException {
    public InvalidReviewTypeException(String message) {
        super(message);
    }
}
