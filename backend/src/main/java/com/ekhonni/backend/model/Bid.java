package com.ekhonni.backend.model;

import com.ekhonni.backend.abstractEntity.AbstractEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Bid extends AbstractEntity {

    @OneToOne
    private User buyer;

    @OneToOne
    private Product product;

    @OneToMany(mappedBy = "bid")
    List<BidLog> bidLog;
}
