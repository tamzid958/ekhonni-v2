package com.ekhonni.backend.dto.role;

import jakarta.validation.constraints.NotNull;

/**
 * Author: Md Jahid Hasan
 * Date: 2/26/25
 */
public record RoleUpdateDTO(
        @NotNull(message = "name cannot be null")
        String name,
        @NotNull(message = "description cannot be null")
        String description
) {
}
