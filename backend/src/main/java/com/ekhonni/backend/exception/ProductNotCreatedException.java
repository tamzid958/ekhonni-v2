/**
 * Author: Rifat Shariar Sakil
 * Time: 3:20 PM
 * Date: 1/10/2025
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.exception;

public class ProductNotCreatedException extends RuntimeException {
    public ProductNotCreatedException(String message) {
        super(message);
    }
}
