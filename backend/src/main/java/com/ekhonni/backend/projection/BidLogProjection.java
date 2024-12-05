package com.ekhonni.backend.projection;

import java.util.UUID;

public interface BidLogProjection {
    UUID getId();

    // BidProjection getBid();

    // UserProjection getUser();

    Double getAmount();

    String getStatus();

}
