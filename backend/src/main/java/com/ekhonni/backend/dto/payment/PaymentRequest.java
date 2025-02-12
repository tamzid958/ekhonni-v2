package com.ekhonni.backend.dto.payment;

import com.ekhonni.backend.enums.PaymentMethod;
import jakarta.validation.constraints.NotNull;

/**
 * Author: Asif Iqbal
 * Date: 2/10/25
 */
public record PaymentRequest(
        @NotNull(message = "Bid id cannot be null")
        Long bidId,
        @NotNull(message = "Payment method cannot be null")
        PaymentMethod paymentMethod
) {
}
