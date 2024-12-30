package com.ekhonni.backend.projection;

import java.util.UUID;

public interface BidLogProjection {
    Long getId();
    Long getBidId();
    UUID getBidderId();
    double getAmount();
    String getStatus();
}
