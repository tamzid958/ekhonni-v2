package com.ekhonni.backend.exception;

/**
 * Author: Md Jahid Hasan
 * Date: 12/9/24
 */
public class UserAlreadyDeletedException extends RuntimeException {
    @Override
    public String getMessage() {
        return "User already deleted.";
    }
}
