package com.ekhonni.backend.dto;

import com.ekhonni.backend.enums.AccountStatus;
import jakarta.validation.constraints.NotBlank;

/**
 * Author: Asif Iqbal
 * Date: 12/9/24
 */

public record AccountUpdateDTO(
                      @NotBlank(message = "Status cannot be blank.")
                      AccountStatus status) {
}
