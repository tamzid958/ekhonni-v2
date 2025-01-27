package com.ekhonni.backend.dto.bid;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import io.swagger.v3.oas.annotations.media.Schema;


@Schema(description = "DTO for updating bid information")
public record BidUpdateDTO(
        @Schema(description = "The new bid amount. Must be greater than previous amount", example = "150.00")
        @NotNull(message = "Amount cannot be null")
        @Digits(integer = 10, fraction = 2, message = "Amount must have up to 10 integer digits and 2 decimal places")
        @Positive
        Double amount) {
}
