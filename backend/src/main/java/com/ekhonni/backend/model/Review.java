package com.ekhonni.backend.model;

import com.ekhonni.backend.baseentity.BaseEntity;
import com.ekhonni.backend.enums.ReviewType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * Author: Asif Iqbal
 * Date: 1/27/25
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Review extends BaseEntity<Long> {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bid_id", nullable = false)
    private Bid bid;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ReviewType type;

    @NotNull
    @Min(1)
    @Max(5)
    private int rating;

    @Column(length = 1000)
    private String description;


    public User getReviewedUser() {
        return type == ReviewType.SELLER
                ? bid.getProduct().getSeller()
                : bid.getBidder();
    }

    public User getReviewer() {
        return type == ReviewType.SELLER
                ? bid.getBidder()
                : bid.getProduct().getSeller();
    }

}
