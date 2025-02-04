package com.ekhonni.backend.exception.prvilege;

/**
 * Author: Md Jahid Hasan
 * Date: 1/5/25
 */
public class NoResourceFoundException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Resource not found.";
    }
}
