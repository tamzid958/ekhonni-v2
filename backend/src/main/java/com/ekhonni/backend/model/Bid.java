package com.ekhonni.backend.model;

import com.ekhonni.backend.baseentity.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Bid extends BaseEntity<Long> {

    @OneToMany(mappedBy = "bid", cascade = CascadeType.ALL)
    private List<BidLog> bidLog;

    @OneToOne
    private User buyer;

    @OneToOne
    private Product product;
}

