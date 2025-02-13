package com.ekhonni.backend.projection.transaction;

import com.ekhonni.backend.enums.TransactionStatus;
import com.ekhonni.backend.util.AuthUtil;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Author: Asif Iqbal
 * Date: 2/3/25
 */
public interface TransactionProjection {

    Long getId();

    @JsonProperty("buyerId")
    UUID getBidBidderId();

    @JsonProperty("buyerName")
    String getBidBidderName();

    @JsonProperty("productId")
    Long getBidProductId();

    @JsonProperty("sellerId")
    UUID getBidProductSellerId();

    @JsonProperty("sellerName")
    String getBidProductSellerName();

    @JsonProperty("amount")
    Double getBidAmount();

    @JsonProperty("currency")
    String getBidCurrency();

    @JsonProperty("status")
    TransactionStatus getStatus();

    LocalDateTime getCreatedAt();
    LocalDateTime getProcessedAt();

    @JsonProperty("type")
    default String getTransactionType() {
        return getBidBidderId().equals(AuthUtil.getAuthenticatedUser().getId()) ? "SENT" : "RECEIVED";
    }

}
