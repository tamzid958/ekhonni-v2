/**
 * Author: Rifat Shariar Sakil
 * Time: 8:56 PM
 * Date: 12/9/2024
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.projection;

import com.ekhonni.backend.model.Category;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

public interface CategoryProjection {
    Long getId();
    String getName();
    boolean isActive();
    List<CategoryProjection> getSubCategories();
}
