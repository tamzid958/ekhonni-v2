/**
 * Author: Rifat Shariar Sakil
 * Time: 3:50 PM
 * Date: 1/2/2025
 * Project Name: backend
 */

package com.ekhonni.backend.exception;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(String message) {
        super(message);
    }

}
