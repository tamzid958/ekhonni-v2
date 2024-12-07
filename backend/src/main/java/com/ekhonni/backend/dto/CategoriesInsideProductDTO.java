/**
 * Author: Rifat Shariar Sakil
 * Time: 6:40 PM
 * Date: 12/7/2024
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.dto;

import com.ekhonni.backend.model.Category;

public class CategoriesInsideProductDTO {
    private Long id;
    private String name;
    private boolean active;

    public CategoriesInsideProductDTO(Category category){

        this.id = category.getId();
        this.name = category.getName();
        this.active = category.isActive();

    }
}
