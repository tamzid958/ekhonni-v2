package com.ekhonni.backend.projection.bid;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Author: Asif Iqbal
 * Date: 1/14/25
 */

public interface BidderBidProjection {
    Long getId();
    Double getAmount();
    String getStatus();
    String getCurrency();
    Long getProductId();
    UUID getProductSellerId();
    String getProductName();
    String getProductSellerName();
    String getProductSellerAddress();
    LocalDateTime getCreatedAt();
}
