package com.ekhonni.backend.projection.bid;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Author: Asif Iqbal
 * Date: 1/14/25
 */

public interface BidderBidProjection {
    Long getId();
    Long getProductId();
    String getProductName();
    UUID getSellerId();
    String getSellerName();
    Double getAmount();
    String getStatus();
    String getCurrency();
    LocalDateTime getCreatedAt();
}
