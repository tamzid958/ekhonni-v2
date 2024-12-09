package com.ekhonni.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Author: Asif Iqbal
 * Date: 12/9/24
 */

public record AccountDTO(
                      @NotNull(message = "Balance cannot be null.")
                      double balance,
                      @NotBlank(message = "Status cannot be blank.")
                      String status) {
}
