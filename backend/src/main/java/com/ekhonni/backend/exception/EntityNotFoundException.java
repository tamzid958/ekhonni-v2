package com.ekhonni.backend.exception;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * Author: Asif Iqbal
 * Date: 12/16/24
 */
@NoArgsConstructor
@AllArgsConstructor
public class EntityNotFoundException extends RuntimeException {
    private String entityName;
    @Override
    public String getMessage() {
        return String.format("%s Not found.", entityName);
    }
}
