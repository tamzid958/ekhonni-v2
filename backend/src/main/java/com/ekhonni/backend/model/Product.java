/**
 * Author: Rifat Shariar Sakil
 * Time: 2:46 PM
 * Date: 12/3/2024
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.model;

import com.ekhonni.backend.baseentity.BaseEntity;
import com.ekhonni.backend.dto.ProductCategoryDTO;
import com.ekhonni.backend.dto.ProductSellerDTO;
import com.ekhonni.backend.enums.ProductCondition;
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
@ToString
public class Product extends BaseEntity<Long> {

    @NotBlank
    private String name;

    @Positive
    @Column(nullable = false)
    private Double price;

    @NotBlank
    private String description;

    @NotBlank
    private String location;

    private boolean approved;
    private boolean sold;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductCondition condition;

    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne(optional = false)
    @JoinColumn(name = "seller_id", nullable = false)
    private User seller;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "product_id")
    private List<ProductImage> images;


    public Long getCategoryId() {
        return this.getCategory().getId();
    }

    public String getCategoryName() {
        return this.getCategory().getName();
    }

    public String getSellerName() {
        return this.getSeller().getUsername();
    }

    public ProductSellerDTO getSellerDTO() {
        return new ProductSellerDTO(this.getSeller().getId(), this.getSeller().getName());
    }

    public ProductCategoryDTO getCategoryDTO() {
        return new ProductCategoryDTO(this.getCategory().getId(), this.getCategory().getName());
    }


}

