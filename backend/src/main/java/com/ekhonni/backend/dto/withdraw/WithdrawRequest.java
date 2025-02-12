package com.ekhonni.backend.dto.withdraw;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * Author: Asif Iqbal
 * Date: 2/3/25
 */
public record WithdrawRequest(
        @NotNull(message = "Payout Account ID is required")
        Long payoutAccountId,

        @NotNull(message = "Amount is required")
        @Min(value = 50, message = "Minimum withdrawal amount is 50")
        Double amount
) {}
