package com.ekhonni.backend.dto.user;

import jakarta.validation.constraints.NotBlank;

/**
 * Author: Md Jahid Hasan
 * Date: 12/22/24
 */
public record PasswordChangeDTO(@NotBlank(message = "password cannot be blank")
                                String currentPassword,
                                @NotBlank(message = "password cannot be blank")
                                String newPassword) {
}
