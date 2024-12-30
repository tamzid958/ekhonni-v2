package com.ekhonni.backend.dto;

import com.ekhonni.backend.dto.BidCreateDTO;
import com.ekhonni.backend.enums.BidStatus;

import java.util.UUID;

public record BidResponseDTO(
        Long bidId,
        Long productId,
        UUID bidderId,
        Double amount,
        String currency,
        BidStatus status
) {
    // Factory method to create a response DTO from the original DTO and additional fields
    public static BidResponseDTO from(BidCreateDTO createDTO, Long bidId, BidStatus status) {
        return new BidResponseDTO(
                bidId,
                createDTO.productId(),
                createDTO.bidderId(),
                createDTO.amount(),
                createDTO.currency(),
                status
        );
    }
}