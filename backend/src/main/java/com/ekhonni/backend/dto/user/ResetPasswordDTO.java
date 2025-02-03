package com.ekhonni.backend.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ResetPasswordDTO(@NotNull(message = "password cannot be null")
                               @NotBlank(message = "password cannot be blank")
                               String newPassword) {
}
