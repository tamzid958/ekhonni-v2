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
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor


public class CategoryDTO extends BaseEntity {

    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String name;
    private boolean active;
    private List<CategoryDTO> children;

    public CategoryDTO(Category category) {

        this.id = category.getId();
        this.createdAt = category.getCreatedAt();
        this.updatedAt = category.getUpdatedAt();
        this.name = category.getName();
        this.active = category.isActive();
        if (category.getChildren() != null) {
            this.children = category.getChildren().stream()
                    .map(CategoryDTO::new)
                    .collect(Collectors.toList());
        }
    }
}
