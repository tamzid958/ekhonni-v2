/**
 * Author: Rifat Shariar Sakil
 * Time: 12:08 PM
 * Date: 12/8/2024
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.dto;

import com.ekhonni.backend.base.BaseEntity;
import com.ekhonni.backend.enums.ProductCondition;
import com.ekhonni.backend.model.Category;
import com.ekhonni.backend.model.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;



public class ProductDTO extends BaseEntity {
//
//    private Long id;
//    private LocalDateTime createdAt;
//    private LocalDateTime updatedAt;
//
//    private String name;
//    private Long price;
//    private String description;
//    private boolean approved ;
//    private boolean sold ;
//    private List<Category> categories;
//
//    public ProductDTO(Product product){
//        this.id = product.getId();
//        this.createdAt = product.getCreatedAt();
//        this.updatedAt = product.getUpdatedAt();
//        this.name = product.getName();
//        this.price = product.getPrice();
//        this.description = product.getDescription();
//        this.approved = product.isApproved();
//        this.sold = product.isSold();
//        if (product.getCategories() != null) {
//            this.categories= product.getCategories().stream()
//                    .map(ProductDTO::new)
//                    .collect(Collectors.toList());
//        }
//    }
}
