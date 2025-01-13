/**
 * Author: Rifat Shariar Sakil
 * Time: 1:48 PM
 * Date: 1/9/25
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.dto;

import com.ekhonni.backend.dto.bid.BidResponseDTO;
import com.ekhonni.backend.enums.ProductCondition;
import com.ekhonni.backend.enums.ProductStatus;
import com.ekhonni.backend.model.ProductImage;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({
        "id",
        "price",
        "name",
        "description",
        "status",
        "createdAt",
        "updatedAt",
        "condition",
        "seller",
        "category",
        "images",
        "bids"
})
public class ProductResponseDTO {
    private Long id;
    private Double price;
    private String name;
    private String description;
    private ProductStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private ProductCondition condition;
    private ProductSellerDTO seller;
    private ProductCategoryDTO category;
    private List<ProductImage> images = new ArrayList<>();
    private List<BidResponseDTO> bids = new ArrayList<>();
}
