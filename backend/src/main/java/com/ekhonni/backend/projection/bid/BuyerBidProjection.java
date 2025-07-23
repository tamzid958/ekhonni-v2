package com.ekhonni.backend.projection.bid;

import com.ekhonni.backend.enums.BidStatus;

import java.time.LocalDateTime;

/**
 * Author: Asif Iqbal
 * Date: 1/10/25
 */

public interface BuyerBidProjection {
    Long getId();
    Double getAmount();
    BidStatus getStatus();
    String getCurrency();
    LocalDateTime getCreatedAt();
}
