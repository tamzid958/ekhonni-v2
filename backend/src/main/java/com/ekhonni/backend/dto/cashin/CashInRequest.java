package com.ekhonni.backend.dto.cashin;

import com.ekhonni.backend.enums.PaymentMethod;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;

/**
 * Author: Asif Iqbal
 * Date: 2/10/25
 */
public record CashInRequest(
        @NotNull(message = "Amount cannot be null")
        @Digits(integer = 10, fraction = 0, message = "Amount must have up to 10 integer digits and 0 decimal places")
        Double amount,
        @NotNull(message = "Payment method cannot be null")
        PaymentMethod paymentMethod
) {
}
