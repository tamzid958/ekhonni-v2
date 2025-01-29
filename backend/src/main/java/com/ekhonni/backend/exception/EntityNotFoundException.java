package com.ekhonni.backend.exception;

/**
 * Author: Asif Iqbal
 * Date: 12/16/24
 */
public class EntityNotFoundException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Not found.";
    }
}
