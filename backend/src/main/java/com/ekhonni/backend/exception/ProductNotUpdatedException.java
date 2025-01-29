/**
 * Author: Rifat Shariar Sakil
 * Time: 3:31 PM
 * Date: 1/10/2025
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.exception;

public class ProductNotUpdatedException extends RuntimeException {
    public ProductNotUpdatedException(String message) {
        super(message);
    }
}
