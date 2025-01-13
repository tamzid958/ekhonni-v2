/**
 * Author: Rifat Shariar Sakil
 * Time: 8:47 PM
 * Date: 1/8/25
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.projection;

import com.ekhonni.backend.dto.ProductCategoryDTO;
import com.ekhonni.backend.dto.ProductSellerDTO;
import com.ekhonni.backend.enums.ProductCondition;
import com.ekhonni.backend.enums.ProductStatus;
import com.ekhonni.backend.model.ProductImage;

import java.time.LocalDateTime;
import java.util.List;

public interface ProductProjection {
    Long getId();

    Double getPrice();

    String getName();

    String getDescription();

    ProductStatus getStatus();

    LocalDateTime getCreatedAt();

    LocalDateTime getUpdatedAt();

    ProductCondition getCondition();

    ProductCategoryDTO getCategoryDTO();

    ProductSellerDTO getSellerDTO();

    List<ProductImage> getImages();

}