package com.ekhonni.backend.projection.bidlog;

import java.time.LocalDateTime;

/**
 * Author: Asif Iqbal
 * Date: 2/12/25
 */
public interface BidLogProjection {
    Long getId();
    Long getOriginalBidId();
    Long getProductId();
    String getBidData();
    LocalDateTime getCreatedAt();
    LocalDateTime getUpdatedAt();
}
