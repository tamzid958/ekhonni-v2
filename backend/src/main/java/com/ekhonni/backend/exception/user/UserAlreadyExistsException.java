package com.ekhonni.backend.exception.user;

public class UserAlreadyExistsException extends RuntimeException {
    @Override
    public String getMessage() {
        return "User already exists.";
    }
}
