package com.ekhonni.backend.dto.refund;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * Author: Asif Iqbal
 * Date: 1/21/25
 */

public record RefundApproveDTO(
        @NotNull(message = "Amount cannot be null")
        @Digits(integer = 10, fraction = 2, message = "Amount must have up to 10 integer digits and 2 decimal places")
        @Positive
        Double amount
) {
}
