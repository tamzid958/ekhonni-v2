package com.ekhonni.backend.dto;

import com.ekhonni.backend.enums.BlockPolicy;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

/**
 * Author: Md Jahid Hasan
 * Date: 2/3/25
 */
public record UserBlockDTO(@NotNull(message = "id cannot be null")
                           UUID id,
                           @NotBlank(message = "Reason can not be blank")
                           String reason,
                           @NotNull(message = "Block Policy can not be null")
                           BlockPolicy blockPolicy
) {
}
