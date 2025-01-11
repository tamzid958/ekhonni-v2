package com.ekhonni.backend.projection;

import java.time.LocalDateTime;
import java.util.UUID;

public interface BuyerBidProjection {
    Long getProductId();
    Double getAmount();
    String getStatus();
    String getCurrency();
    LocalDateTime getCreatedAt();
}
