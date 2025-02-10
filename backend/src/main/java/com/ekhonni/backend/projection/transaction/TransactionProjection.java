package com.ekhonni.backend.projection.transaction;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

/**
 * Author: Asif Iqbal
 * Date: 2/3/25
 */
public interface TransactionProjection {
    Long getId();
    @JsonProperty("buyerId")
    Long getBidderId();
    @JsonProperty("buyerName")
    Long getBidderName();
    @JsonProperty("productId")
    Long getBidProductId();
    @JsonProperty("sellerId")
    Long getBidProductSellerId();
    @JsonProperty("sellerName")
    Long getBidProductSellerName();
    Double getAmount();
    String getCurrency();
    LocalDateTime getProcessedAt();
}
