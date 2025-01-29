package com.ekhonni.backend.exception;

/**
 * Author: Md Jahid Hasan
 * Date: 1/27/25
 */
public class RefreshTokenNotFoundException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Invalid Refresh Token";
    }
}
