package com.ekhonni.backend.model;

import com.ekhonni.backend.baseentity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

/**
 * Author: Md Sakil Ahmed
 * Date: 06/01/25
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Review extends BaseEntity<Long> {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id", nullable = false)
    private User buyer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id", nullable = false)
    private User seller;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bid_id", nullable = false)
    private Bid bid;

    @Column(nullable = false)
    private Integer rating;

    @Column(nullable = true)
    private String comment;

    private Boolean isDeleted = false;
}
