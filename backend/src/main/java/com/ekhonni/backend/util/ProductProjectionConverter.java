/**
 * Author: Rifat Shariar Sakil
 * Time: 6:56 PM
 * Date: 1/9/25
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.util;

import com.ekhonni.backend.dto.ProductResponseDTO;
import com.ekhonni.backend.projection.ProductProjection;

public class ProductProjectionConverter {
    public static ProductResponseDTO convert(ProductProjection projection) {
        ProductResponseDTO dto = new ProductResponseDTO();
        dto.setId(projection.getId());
        dto.setPrice(projection.getPrice());
        dto.setName(projection.getName());
        dto.setDescription(projection.getDescription());
        dto.setCreatedAt(projection.getCreatedAt());
        dto.setUpdatedAt(projection.getUpdatedAt());
        dto.setCondition(projection.getCondition());
        dto.setCategory(projection.getCategoryDTO());
        dto.setSeller(projection.getSellerDTO());
        dto.setImages(projection.getImages());
        dto.setStatus(projection.getStatus());
        return dto;
    }
}
