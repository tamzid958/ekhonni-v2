package com.ekhonni.backend.exception;

public class ProductNotFoundException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Product not found.";
    }
}
