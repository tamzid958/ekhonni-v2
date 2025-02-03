package com.ekhonni.backend.exception.user;

/**
 * Author: Md Jahid Hasan
 * Date: 1/5/25
 */
public class InvalidJwtTokenException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Invalid JWT Token.";
    }
}
