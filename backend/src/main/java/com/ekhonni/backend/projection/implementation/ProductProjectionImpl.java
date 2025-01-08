/**
 * Author: Rifat Shariar Sakil
 * Time: 8:18 PM
 * Date: 12/9/2024
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.projection.implementation;

import com.ekhonni.backend.enums.ProductCondition;
import com.ekhonni.backend.model.ProductImage;
import com.ekhonni.backend.projection.ProductProjection;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@ToString
public class ProductProjectionImpl implements ProductProjection {
    private Long id;
    private Double price;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private ProductCondition condition;
    private Long categoryId;
    private String categoryName;
    private List<ProductImage> images= new ArrayList<>();

    public ProductProjectionImpl(Long id, Double price, String name, String description,
                                 LocalDateTime createdAt, LocalDateTime updatedAt,
                                 ProductCondition condition, Long categoryId,
                                 String categoryName) {
        this.id = id;
        this.price = price;
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.condition = condition;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }
}
