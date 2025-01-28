package com.ekhonni.backend.projection.review;

import com.ekhonni.backend.enums.ReviewType;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Author: Asif Iqbal
 * Date: 1/28/25
 */
public interface BuyerReviewProjection {

    Long getId();
    Long getBidId();
    ReviewType getType();
    Integer getRating();
    String getDescription();

    @JsonProperty("productId")
    Long getBidProductId();

    @JsonProperty("reviewerId")
    UUID getBidProductSellerId();

    @JsonProperty("reviewerName")
    String getBidProductSellerName();

    @JsonProperty("revieweeId")
    UUID getBidBidderId();

    @JsonProperty("revieweeName")
    String getBidBidderName();

    @JsonProperty("lastModified")
    LocalDateTime getCreatedAt();

}
