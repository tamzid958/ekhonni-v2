package com.ekhonni.backend.dto;

import java.util.UUID;

public record BidLogCreateDTO(Long bidId, UUID bidderId, Double amount) {

}
