/**
 * Author: Rifat Shariar Sakil
 * Time: 9:28 PM
 * Date: 2/1/2025
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.dto.category;


import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CategoryTreeDTO {
    private Long id;
    private String name;
    private boolean active;
    private List<CategoryTreeDTO> children = new ArrayList<>();

    public CategoryTreeDTO(Long id, String name, boolean active) {
        this.id = id;
        this.name = name;
        this.active = active;
    }

    public void addChild(CategoryTreeDTO child) {
        this.children.add(child);
    }
}
