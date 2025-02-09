package com.ekhonni.backend.dto.bid;

import com.ekhonni.backend.enums.BidStatus;
import com.ekhonni.backend.model.Bid;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class BidArchiveDTO {
    private UUID bidderId;
    private Long productId;
    private double amount;
    private String currency;
    private BidStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static BidArchiveDTO fromBid(Bid bid) {
        BidArchiveDTO dto = new BidArchiveDTO();
        dto.setBidderId(bid.getBidder().getId());
        dto.setProductId(bid.getProduct().getId());
        dto.setAmount(bid.getAmount());
        dto.setCurrency(bid.getCurrency());
        dto.setStatus(bid.getStatus());
        dto.setCreatedAt(bid.getCreatedAt());
        dto.setUpdatedAt(bid.getUpdatedAt());
        return dto;
    }
}