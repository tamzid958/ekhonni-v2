package com.ekhonni.backend.exception;

public class UserAlreadyDeletedException extends RuntimeException {
    public UserAlreadyDeletedException(String errorMessage) {
        super(errorMessage);
    }
}
