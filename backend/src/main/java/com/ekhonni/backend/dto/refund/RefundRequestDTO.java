package com.ekhonni.backend.dto.refund;

import jakarta.validation.constraints.NotBlank;

/**
 * Author: Asif Iqbal
 * Date: 1/21/25
 */

public record RefundRequestDTO(
        @NotBlank(message = "Remarks cannot be blank")
        String remarks
) {
}
