package com.ekhonni.backend.dto.role;

import jakarta.validation.constraints.NotBlank;

/**
 * Author: Asif Iqbal
 * Date: 2/16/25
 */
public record RoleCreateDTO(
        @NotBlank(message = "Name cannot be blank")
        String name,
        @NotBlank(message = "Description cannot be blank")
        String description
) {
}