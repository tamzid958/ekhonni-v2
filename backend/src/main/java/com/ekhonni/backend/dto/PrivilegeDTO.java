package com.ekhonni.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Author: Md Jahid Hasan
 * Date: 12/29/24
 */
public record PrivilegeDTO(@NotNull(message = "name cannot be null")
                           @NotBlank(message = "name cannot be blank")
                           String name,
                           @NotNull(message = "description cannot be null")
                           @NotBlank(message = "description cannot be blank")
                           String description,
                           @NotNull(message = "type cannot be null")
                           @NotBlank(message = "type cannot be blank")
                           String type,
                           @NotNull(message = "httpMethod cannot be null")
                           @NotBlank(message = "httpMethod cannot be blank")
                           String httpMethod,
                           @NotNull(message = "endpoint cannot be null")
                           @NotBlank(message = "endpoint cannot be blank")
                           String endpoint
) {
}
