package com.ekhonni.backend.projection;

import java.time.LocalDateTime;
import java.util.UUID;

public interface SellerBidProjection {
    Long getId();
    Long getProductId();
    UUID getBidderId();
    String getBidderName();
    String getBidderAddress();
    Double getAmount();
    String getStatus();
    String getCurrency();
    LocalDateTime getCreatedAt();
}
