package com.ekhonni.backend.exception.prvilege;

/**
 * Author: Md Jahid Hasan
 * Date: 12/30/24
 */
public class PrivilegeNotFoundException extends RuntimeException {
    public PrivilegeNotFoundException(String message) {
        super(message);
    }
}
