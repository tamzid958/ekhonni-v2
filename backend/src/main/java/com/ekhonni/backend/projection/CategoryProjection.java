/**
 * Author: Rifat Shariar Sakil
 * Time: 8:56 PM
 * Date: 12/9/2024
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.projection;

import com.ekhonni.backend.projection.category.ViewerCategoryProjection;

public interface CategoryProjection extends ViewerCategoryProjection {
    
    boolean isActive();
}
