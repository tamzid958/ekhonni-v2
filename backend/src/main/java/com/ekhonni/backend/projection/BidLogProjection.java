package com.ekhonni.backend.projection;

public interface BidLogProjection {

    Long getId();

    // BidProjection getBid();

    UserProjection getBidder();

    Double getAmount();

    String getStatus();

}
