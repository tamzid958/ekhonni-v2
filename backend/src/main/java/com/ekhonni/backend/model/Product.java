/**
 * Author: Rifat Shariar Sakil
 * Time: 2:46 PM
 * Date: 12/3/2024
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.model;

import com.ekhonni.backend.baseentity.BaseEntity;
import com.ekhonni.backend.dto.product.ProductCategoryDTO;
import com.ekhonni.backend.dto.product.ProductImageDTO;
import com.ekhonni.backend.dto.product.ProductSellerAndBuyerDTO;
import com.ekhonni.backend.enums.Division;
import com.ekhonni.backend.enums.ProductCondition;
import com.ekhonni.backend.enums.ProductStatus;
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
    private String title;

    @NotBlank
    private String subTitle;

    @NotBlank
    private String description;

    @Positive
    @Column(nullable = false)
    private Double price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Division division;

    @NotBlank
    private String address;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductCondition condition;

    @NotBlank
    private String conditionDetails;


    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id", nullable = false)
    private User seller;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private List<ProductImage> images;


    public ProductSellerAndBuyerDTO getSellerDTO() {
        return new ProductSellerAndBuyerDTO(this.getSeller().getId(), this.getSeller().getName());
    }


    public ProductCategoryDTO getCategoryDTO() {
        return new ProductCategoryDTO(this.getCategory().getId(), this.getCategory().getName());
    }

    public List<ProductImageDTO> getImagesDTO() {
        return this.getImages()
                .stream()
                .map(image -> new ProductImageDTO(image.getImagePath()))
                .toList();
    }


}

