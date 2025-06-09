package com.ekhonni.backend.projection.bid;

import com.ekhonni.backend.enums.BidStatus;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Author: Asif Iqbal
 * Date: 1/10/25
 */

public interface AdminBidProjection {
    Long getId();
    UUID getBidderId();
    UUID getProductSellerId();
    Long getProductId();
    String getBidderName();
    String getBidderAddress();
    Double getAmount();
    BidStatus getStatus();
    String getCurrency();
    LocalDateTime getCreatedAt();
    String getProductTitle();
    String getProductSellerName();
    String getProductSellerAddress();
}
