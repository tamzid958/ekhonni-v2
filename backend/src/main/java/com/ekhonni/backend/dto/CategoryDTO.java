/**
 * Author: Rifat Shariar Sakil
 * Time: 11:20 AM
 * Date: 12/8/2024
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.dto;


import com.ekhonni.backend.base.BaseEntity;
import com.ekhonni.backend.model.Category;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO extends BaseEntity {

    private Long id;
    private String name;
    private boolean active;
    private List<CategoryDTO> childrenCategory;

    public CategoryDTO(Category category) {

        this.id = category.getId();
        this.name = category.getName();
        this.active = category.isActive();
        if (category.getChildrenCategory() != null) {
            this.childrenCategory = category.getChildrenCategory().stream()
                    .map(CategoryDTO::new)
                    .collect(Collectors.toList());
        }
    }
}
