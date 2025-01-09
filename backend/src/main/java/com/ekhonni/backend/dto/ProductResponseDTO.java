/**
 * Author: Rifat Shariar Sakil
 * Time: 1:48 PM
 * Date: 1/9/25
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.dto;

import com.ekhonni.backend.enums.ProductCondition;
import com.ekhonni.backend.model.ProductImage;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class ProductResponseDTO {
    private Long id;
    private Double price;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private ProductCondition condition;
    private ProductSellerDTO seller;
    private ProductCategoryDTO category;

    private List<ProductImage> images = new ArrayList<>();
    private List<BidResponseDTO> bids = new ArrayList<>();


    public ProductResponseDTO(Long id, Double price, String name, String description,
                              LocalDateTime createdAt, LocalDateTime updatedAt,
                              ProductCondition condition, UUID sellerId, String sellerName, Long categoryId, String categoryName) {
        this.id = id;
        this.price = price;
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.condition = condition;
        this.seller = new ProductSellerDTO(sellerId,sellerName);
        this.category = new ProductCategoryDTO(categoryId,categoryName);
    }

}
