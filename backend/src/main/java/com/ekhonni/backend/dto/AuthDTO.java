package com.ekhonni.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Author: Md Jahid Hasan
 * Date: 12/10/24
 */

public record AuthDTO(@NotNull @NotBlank @Email
                      String email,
                      @NotNull @NotBlank
                      String password) {
}
