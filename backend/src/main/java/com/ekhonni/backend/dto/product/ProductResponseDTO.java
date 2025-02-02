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
        "category",
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
    private ProductSellerDTO seller;
    private ProductCategoryDTO category;
    private List<ProductImageDTO> images = new ArrayList<>();
}
