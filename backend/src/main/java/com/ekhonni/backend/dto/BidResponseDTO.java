package com.ekhonni.backend.dto;

import com.ekhonni.backend.enums.BidStatus;

public record BidResponseDTO(
        Long id,
        Long productId,
        Double amount,
        String currency,
        BidStatus status
) {
    // Factory method to create a response DTO from the original DTO and additional fields
    public static BidResponseDTO from(BidCreateDTO createDTO, Long id, BidStatus status) {
        return new BidResponseDTO(
                id,
                createDTO.productId(),
                createDTO.amount(),
                createDTO.currency(),
                status
        );
    }
}