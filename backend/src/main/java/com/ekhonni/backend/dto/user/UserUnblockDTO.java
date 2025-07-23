package com.ekhonni.backend.dto.user;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

/**
 * Author: Md Jahid Hasan
 * Date: 2/4/25
 */
public record UserUnblockDTO(@NotNull(message = "id cannot be null")
                             UUID id) {
}
