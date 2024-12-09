package com.ekhonni.backend.exception;

public class UserAlreadyExistsException extends RuntimeException {
    @Override
    public String getMessage() {
        return "User already exists.";
    }
}
