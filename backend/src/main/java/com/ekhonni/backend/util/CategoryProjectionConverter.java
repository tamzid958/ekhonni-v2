/**
 * Author: Rifat Shariar Sakil
 * Time: 6:43 PM
 * Date: 1/12/2025
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.util;

import com.ekhonni.backend.dto.category.CategorySubCategoryDTOV2;
import com.ekhonni.backend.projection.category.ViewerCategoryProjection;

public class CategoryProjectionConverter {
    public static CategorySubCategoryDTOV2 convert(ViewerCategoryProjection projection) {
        CategorySubCategoryDTOV2 dto = new CategorySubCategoryDTOV2();
//        dto.setName(projection.getName());
        return dto;
    }

}
