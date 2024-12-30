package com.ekhonni.backend.exception;

/**
 * Author: Md Jahid Hasan
 * Date: 12/29/24
 */
public class RoleNotFoundException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Role not found.";
    }
}
