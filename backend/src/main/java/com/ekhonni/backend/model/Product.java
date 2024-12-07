package com.ekhonni.backend.model;

import com.ekhonni.backend.baseentity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product extends BaseEntity<Long> {

    private String name;

    @OneToOne(mappedBy = "product")
    Bid bid;
}
