package com.ekhonni.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Author: Md Jahid Hasan
 * Date: 12/10/24
 */

public record AuthDTO(@NotNull(message = "email cannot be null")
                      @NotBlank(message = "email cannot be blank")
                      @Email(message = "Not a valid email")
                      String email,
                      @NotNull(message = "password cannot be null")
                      @NotBlank(message = "password cannot be blank")
                      String password) {
}
