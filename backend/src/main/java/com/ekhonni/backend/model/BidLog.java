package com.ekhonni.backend.model;

import com.ekhonni.backend.baseentity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class BidLog extends BaseEntity<Long> {

    @ManyToOne
    private Bid bid;

    @ManyToOne
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
