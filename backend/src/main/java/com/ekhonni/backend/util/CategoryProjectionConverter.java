/**
 * Author: Rifat Shariar Sakil
 * Time: 6:43 PM
 * Date: 1/12/2025
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.util;

import com.ekhonni.backend.dto.category.CategorySubCategoryDTO;
import com.ekhonni.backend.projection.category.ViewerCategoryProjection;

public class CategoryProjectionConverter {
    public static CategorySubCategoryDTO convert(ViewerCategoryProjection projection) {
        CategorySubCategoryDTO dto = new CategorySubCategoryDTO();
//        dto.setName(projection.getName());
        return dto;
    }

}
