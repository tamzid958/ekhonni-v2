/**
 * Author: Rifat Shariar Sakil
 * Time: 7:16 PM
 * Date: 2/19/25
 * Project Name: ekhonni-v2
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
    private String name;
    private List<String> subCategories;
    private List<String> chainCategories;
}