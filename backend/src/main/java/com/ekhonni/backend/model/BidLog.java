package com.ekhonni.backend.model;

import com.ekhonni.backend.baseentity.BaseEntity;
import com.ekhonni.backend.enums.BidLogStatus;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class BidLog extends BaseEntity<Long> {

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "bid_id", nullable = false)
    private Bid bid;

    @ManyToOne
    @JoinColumn(name = "bidder_id")
    private User bidder;

    private Double amount = 0.0;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BidLogStatus status = BidLogStatus.PENDING;

    public BidLog(Double amount) {
        super();
        this.amount = amount;
    }
}

