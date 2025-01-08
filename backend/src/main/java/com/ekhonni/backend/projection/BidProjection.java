package com.ekhonni.backend.projection;

import java.util.UUID;

public interface BidProjection {
    Long getId();

    Long getProductId();

    UUID getBidderId();

    String getBidderName();

    Double getAmount();

    String getStatus();
}
