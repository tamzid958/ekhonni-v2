package com.ekhonni.backend.exception.review;

/**
 * Author: Asif Iqbal
 * Date: 1/28/25
 */
public class ReviewAlreadyExistsException extends RuntimeException {
    public ReviewAlreadyExistsException(String message) {
        super(message);
    }
}
