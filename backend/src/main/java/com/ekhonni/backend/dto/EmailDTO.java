package com.ekhonni.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * Author: Md Jahid Hasan
 * Date: 12/22/24
 */
public record EmailDTO(@NotBlank(message = "email cannot be blank")
                       @Email(message = "Not a valid email")
                       String email) {
}
