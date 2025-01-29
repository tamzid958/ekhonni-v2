package com.ekhonni.backend.exception;

/**
 * Author: Md Jahid Hasan
 * Date: 12/30/24
 */
public class RoleAlreadyExistsException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Role already exists.";
    }
}
