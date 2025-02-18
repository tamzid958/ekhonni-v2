/**
 * Author: Rifat Shariar Sakil
 * Time: 6:50 PM
 * Date: 12/29/2024
 * Project Name: backend
 */

package com.ekhonni.backend.dto.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class CategorySubCategoryDTO {
    CategoryDTO category;
    List<CategoryDTO> subCategories;
    private List<String> chainCategories;
}
