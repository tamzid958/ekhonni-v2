/**
 * Author: Rifat Shariar Sakil
 * Time: 6:50 PM
 * Date: 12/29/2024
 * Project Name: backend
 */

package com.ekhonni.backend.dto;

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
    private String name;
    private List<String> subCategories;
    private List<String> chainCategories;
}
