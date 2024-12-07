/**
 * Author: Rifat Shariar Sakil
 * Time: 6:40 PM
 * Date: 12/7/2024
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.dto;

import com.ekhonni.backend.enums.ProductCondition;
import com.ekhonni.backend.model.Category;
import com.ekhonni.backend.model.Product;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

public class ProductDTO {
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String name;
    private Long price;
    private String description;
    private boolean sold;
    private ProductCondition condition;

    private List<CategoriesInsideProductDTO> categories;


    public ProductDTO(Product product){
        this.id = product.getId();
        this.createdAt = product.getCreatedAt();
        this.updatedAt = product.getUpdatedAt();
        this.name = product.getName();
        this.price = product.getPrice();
        this.description = product.getDescription();
        this.sold = product.isSold();
        this.condition = product.getCondition();

        this.categories = product.getCategories().stream()
                .map(CategoriesInsideProductDTO::new)
                .toList();

    }
}
