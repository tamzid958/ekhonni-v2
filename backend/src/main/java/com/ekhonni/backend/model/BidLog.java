package com.ekhonni.backend.model;


import com.ekhonni.backend.baseentity.BaseEntity;
import lombok.*;
import jakarta.persistence.*;

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

}
