/**
 * Author: Rifat Shariar Sakil
 * Time: 2:46 PM
 * Date: 12/3/2024
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.model;

import com.ekhonni.backend.base.BaseEntity;
import com.ekhonni.backend.enums.ProductCondition;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product")

public class Product extends BaseEntity<Long> {

    @NotBlank
    private String name;

    @Positive
    @Column(nullable = false)
    private Long price;

    @NotBlank
    private String description;

    private boolean approved = false;
    private boolean sold = false;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductCondition condition;

    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

}
