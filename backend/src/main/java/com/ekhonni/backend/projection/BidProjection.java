package com.ekhonni.backend.projection;

import java.time.LocalDateTime;
import java.util.UUID;

public interface BidProjection {
    Long getId();
    Long getProductId();
    UUID getBidderId();
    Double getAmount();
    String getStatus();
    LocalDateTime createdAt();
}
