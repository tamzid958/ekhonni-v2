package com.ekhonni.backend.dto.bid;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Data Transfer Object (DTO) for creating a new bid in the system.
 */
@Schema(description = "DTO for creating a new bid")
public record BidCreateDTO(
        @Schema(
                description = "ID of the product being bid on",
                example = "1",
                nullable = false
        )
        @NotNull(message = "Product id cannot be null")
        Long productId,

        @Schema(
                description = "Bid amount with up to 10 integer digits and 2 decimal places",
                example = "1000",
                minimum = "1"
        )
        @NotNull(message = "Amount cannot be null")
        @Digits(integer = 10, fraction = 0, message = "Amount must have up to 10 integer digits")
        @Positive
        Double amount)

    {
}
