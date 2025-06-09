package com.ekhonni.backend.exception.review;

/**
 * Author: Asif Iqbal
 * Date: 1/28/25
 */
public class ReviewNotFoundException extends RuntimeException {
    public ReviewNotFoundException(String message) {
        super(message);
    }
}
