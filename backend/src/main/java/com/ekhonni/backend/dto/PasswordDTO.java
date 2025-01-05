package com.ekhonni.backend.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * Author: Md Jahid Hasan
 * Date: 12/22/24
 */
public record PasswordDTO(@NotBlank(message = "password cannot be blank")
                          String password) {
}
