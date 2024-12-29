/**
 * Author: Rifat Shariar Sakil
 * Time: 8:18 PM
 * Date: 12/9/2024
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.projection;

import com.ekhonni.backend.enums.ProductCondition;

import java.time.LocalDateTime;

public interface ProductProjection {
    Long getId();

    Long getPrice();

    String getName();

    String getDescription();

    LocalDateTime getCreatedAt();

    LocalDateTime getUpdatedAt();

    ProductCondition getCondition();

    Long getCategoryId();

    String getCategoryName();
}
