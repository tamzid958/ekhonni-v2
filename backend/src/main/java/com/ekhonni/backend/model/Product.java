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
public class Product extends AbstractEntity {

    private String name;

    @OneToOne(mappedBy = "product")
    Bid bid;
}
