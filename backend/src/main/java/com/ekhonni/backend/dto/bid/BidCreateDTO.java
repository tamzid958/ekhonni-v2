package com.ekhonni.backend.dto.bid;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record BidCreateDTO(
        @NotNull(message = "Product id cannot be null")
        Long productId,
        @NotNull(message = "Amount cannot be null")
        @Digits(integer = 10, fraction = 2, message = "Amount must have up to 10 integer digits and 2 decimal places")
        @Positive
        Double amount,
        String currency) {

    public BidCreateDTO {
        if (currency == null) {
            currency = "BDT";
        }
    }
}
