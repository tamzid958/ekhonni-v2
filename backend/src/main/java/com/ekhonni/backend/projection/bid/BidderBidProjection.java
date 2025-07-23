package com.ekhonni.backend.projection.bid;

import com.ekhonni.backend.enums.BidStatus;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Author: Asif Iqbal
 * Date: 1/14/25
 */

public interface BidderBidProjection {
    Long getId();
    Double getAmount();
    BidStatus getStatus();
    String getCurrency();
    Long getProductId();
    UUID getProductSellerId();
    String getProductTitle();
    String getProductSellerName();
    String getProductSellerAddress();
    LocalDateTime getCreatedAt();
}
