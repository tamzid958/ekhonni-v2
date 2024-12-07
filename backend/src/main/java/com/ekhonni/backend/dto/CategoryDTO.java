/**
 * Author: Rifat Shariar Sakil
 * Time: 6:07 PM
 * Date: 12/7/2024
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.dto;

import com.ekhonni.backend.model.Category;
import lombok.Data;
import java.util.List;

@Data
public class CategoryDTO {
    private Long id;
    private String name;
    private boolean active;
    private List<ProductsInsideCategoryDTO> products;


    public CategoryDTO(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.active = category.isActive();
        this.products = category.getProducts().stream()
                .map(ProductsInsideCategoryDTO::new)
                .toList();
    }
}
