/**
 * Author: Rifat Shariar Sakil
 * Time: 8:18 PM
 * Date: 12/9/2024
 * Project Name: backend
 */

package com.ekhonni.backend.projection;

import com.ekhonni.backend.enums.ProductCondition;
import com.ekhonni.backend.model.Category;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public interface ProductProjection {
    Long getId();
    Long getPrice();

    String getName();
    String getDescription();

    LocalDateTime getCreatedAt();
    LocalDateTime getUpdatedAt();



    ProductCondition getCondition();

    @JsonIgnoreProperties({"subCategories", "parentCategory"})
    Category getCategory();
}
