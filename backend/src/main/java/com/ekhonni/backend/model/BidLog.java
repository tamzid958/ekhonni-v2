package com.ekhonni.backend.model;

import com.ekhonni.backend.baseentity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class BidLog extends BaseEntity<Long> {

    @ManyToOne
    @JoinColumn(name = "bid_id") // Ensures proper foreign key mapping
    private Bid bid;

    @ManyToOne
    @JoinColumn(name = "bidder_id")
    private User bidder;

    @Column
    private Double amount;

    @Column
    private String status;

    public BidLog(Double amount, String status){
        this.amount = amount;
        this.status = status;
    }

}

