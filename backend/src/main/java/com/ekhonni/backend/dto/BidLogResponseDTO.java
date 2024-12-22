package com.ekhonni.backend.dto;

import com.ekhonni.backend.dto.BidLogCreateDTO;
import com.ekhonni.backend.enums.BidLogStatus;

import java.util.UUID;

public record BidLogResponseDTO(
        Long bidLogId,
        Long bidId,
        UUID bidderId,
        Double amount,
        BidLogStatus status
) {
    // Factory method to create a response DTO from the original DTO and additional fields
    public static BidLogResponseDTO from(BidLogCreateDTO createDTO, Long bidLogId, BidLogStatus status) {
        return new BidLogResponseDTO(
                bidLogId,
                createDTO.bidId(),
                createDTO.bidderId(),
                createDTO.amount(),
                status
        );
    }
}