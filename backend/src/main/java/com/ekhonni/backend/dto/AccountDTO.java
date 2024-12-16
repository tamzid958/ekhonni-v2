package com.ekhonni.backend.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Author: Asif Iqbal
 * Date: 12/9/24
 */

public record AccountDTO(
                      @NotNull(message = "Balance cannot be null.")
                      @DecimalMin(value = "0.0", message = "Balance must be greater than or equal to 0")
                      double balance,
                      @NotBlank(message = "Status cannot be blank.")
                      String status) {
}
