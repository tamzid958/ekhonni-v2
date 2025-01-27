package com.ekhonni.backend.dto.bid;

import com.ekhonni.backend.enums.BidStatus;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO representing a bid response")
public record BidResponseDTO(
        @Schema(description = "Unique identifier of the bid", example = "1")
        Long id,

        @Schema(description = "Identifier of the product associated with the bid", example = "101")
        Long productId,

        @Schema(description = "Amount of the bid", example = "99.99")
        Double amount,

        @Schema(description = "Currency of the bid amount", example = "BDT")
        String currency,

        @Schema(description = "Current status of the bid", example = "PENDING")
        BidStatus status) {

    // Factory method to create a response DTO from the original DTO and additional fields
    public static BidResponseDTO from(BidCreateDTO createDTO, Long id, BidStatus status) {
        return new BidResponseDTO(
                id,
                createDTO.productId(),
                createDTO.amount(),
                createDTO.currency(),
                status);
    }
}