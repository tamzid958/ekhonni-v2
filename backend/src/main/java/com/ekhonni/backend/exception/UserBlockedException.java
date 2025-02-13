package com.ekhonni.backend.exception;

/**
 * Author: Md Jahid Hasan
 * Date: 2/2/25
 */
public class UserBlockedException extends RuntimeException {
    public UserBlockedException(String message) {
        super(message);
    }
}
