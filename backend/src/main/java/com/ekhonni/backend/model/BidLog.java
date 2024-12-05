package com.ekhonni.backend.model;

import com.ekhonni.backend.abstractEntity.AbstractEntity;
import lombok.*;
import jakarta.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class BidLog extends AbstractEntity {

    @ManyToOne
    private Bid bid;

    @ManyToOne
    private User bidder;

    @Column
    private Double amount;

    @Column
    private String status;

}
