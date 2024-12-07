/**
 * Author: Rifat Shariar Sakil
 * Time: 6:35 PM
 * Date: 12/7/2024
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.dto;

import com.ekhonni.backend.model.Product;
import lombok.Data;

@Data
public class ProductsInsideCategoryDTO {
    private Long id;
    private String name;
    private String description;
    private Long price;

    public ProductsInsideCategoryDTO(Product product){
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
    }
}
