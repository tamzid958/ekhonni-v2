package com.ekhonni.backend.projection.bid;

import java.time.LocalDateTime;

/**
 * Author: Asif Iqbal
 * Date: 1/10/25
 */

public interface BuyerBidProjection {
    Long getProductId();
    Double getAmount();
    String getStatus();
    String getCurrency();
    LocalDateTime getCreatedAt();
}
