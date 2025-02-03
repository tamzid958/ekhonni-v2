package com.ekhonni.backend.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

/**
 * Author: Md Jahid Hasan
 * Date: 2/3/25
 */
public record UserIdDTO(@NotBlank(message = "id cannot be blank")
                        UUID id) {
}
