package com.ekhonni.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Author: Md Jahid Hasan
 * Date: 12/26/24
 */
public record TokenDTO(@NotNull @NotBlank
                       String token) {
}
