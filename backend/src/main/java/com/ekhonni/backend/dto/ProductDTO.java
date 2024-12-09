/**
 * Author: Rifat Shariar Sakil
 * Time: 12:50 AM
 * Date: 12/9/2024
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.dto;

import com.ekhonni.backend.enums.ProductCondition;
import com.ekhonni.backend.model.Category;
import com.ekhonni.backend.model.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor


public class ProductDTO {

    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String name;
    private Long price;
    private String description;
    private boolean approved = false;
    private boolean sold = false;
    private ProductCondition condition;


    @JsonIgnoreProperties({"subCategories", "parentCategory"})
    private Category category;

    public ProductDTO(Product product) {
        this.id = product.getId();
        this.createdAt = product.getCreatedAt();
        this.updatedAt = product.getUpdatedAt();
        this.name = product.getName();
        this.price = product.getPrice();
        this.description = product.getDescription();
        this.approved = product.isApproved();
        this.sold = product.isSold();
        this.condition = product.getCondition();
        this.category = product.getCategory();
    }
}
