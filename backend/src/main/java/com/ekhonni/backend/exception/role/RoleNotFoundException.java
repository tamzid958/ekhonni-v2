package com.ekhonni.backend.exception.role;

/**
 * Author: Md Jahid Hasan
 * Date: 12/29/24
 */
public class RoleNotFoundException extends RuntimeException {
    public RoleNotFoundException(String message) {
        super(message);
    }

}
