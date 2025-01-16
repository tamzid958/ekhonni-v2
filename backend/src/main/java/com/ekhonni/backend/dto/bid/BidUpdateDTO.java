package com.ekhonni.backend.dto.bid;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record BidUpdateDTO(
        @NotNull(message = "Amount cannot be null")
        @Digits(integer = 10, fraction = 2, message = "Amount must have up to 10 integer digits and 2 decimal places")
        @Positive
        Double amount) {
}
