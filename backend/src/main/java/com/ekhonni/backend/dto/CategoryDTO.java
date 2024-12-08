/**
 * Author: Rifat Shariar Sakil
 * Time: 2:58 PM
 * Date: 12/8/2024
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.dto;

import com.ekhonni.backend.model.Category;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class CategoryDTO {
    private Long id;
    private String name;
    private boolean active;


    @JsonProperty("subCategories")
    private List<CategoryDTO> subCategories;


    public CategoryDTO(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.active = category.isActive();
        this.subCategories = category.getSubCategories().stream()
                .map(CategoryDTO::new)
                .collect(Collectors.toList());
    }
}
