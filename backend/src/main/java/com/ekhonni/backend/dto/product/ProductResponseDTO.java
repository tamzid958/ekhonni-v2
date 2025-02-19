/**
 * Author: Rifat Shariar Sakil
 * Time: 1:48 PM
 * Date: 1/9/25
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.dto.product;

import com.ekhonni.backend.enums.Division;
import com.ekhonni.backend.enums.ProductCondition;
import com.ekhonni.backend.enums.ProductStatus;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({
        "id",
        "title",
        "subTitle",
        "description",
        "price",
        "division",
        "address",
        "condition",
        "conditionDetails",
        "status",
        "createdAt",
        "updatedAt",
        "seller",
        "buyer",
        "category",
        "boostData",
        "images"
})
public class ProductResponseDTO {
    private Long id;
    private String title;
    private String subTitle;
    private String description;
    private Double price;
    private Division division;
    private String address;
    private ProductCondition condition;
    private String conditionDetails;
    private ProductStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private ProductSellerAndBuyerDTO seller;
    private ProductSellerAndBuyerDTO buyer;
    private ProductCategoryDTO category;
    private ProductBoostResponseDTO boostData;
    private List<ProductImageDTO> images = new ArrayList<>();


    public void setBuyer(UUID id, @NotBlank String name) {
        this.buyer = new ProductSellerAndBuyerDTO(id, name);
    }
}
