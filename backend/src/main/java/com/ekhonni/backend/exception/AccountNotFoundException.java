package com.ekhonni.backend.exception;

public class AccountNotFoundException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Account not found.";
    }
}
