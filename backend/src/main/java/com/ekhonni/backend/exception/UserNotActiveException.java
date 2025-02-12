package com.ekhonni.backend.exception;

/**
 * Author: Md Jahid Hasan
 * Date: 2/3/25
 */
public class UserNotActiveException extends RuntimeException {
    public UserNotActiveException(String message) {
        super(message);
    }
}
