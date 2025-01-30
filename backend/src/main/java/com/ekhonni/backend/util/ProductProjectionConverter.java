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
        dto.setTitle(projection.getTitle());
        dto.setSubTitle(projection.getSubTitle());
        dto.setDescription(projection.getDescription());
        dto.setPrice(projection.getPrice());

        dto.setDivision(projection.getDivision());
        dto.setAddress(projection.getAddress());

        dto.setCondition(dto.getCondition());
        dto.setConditionDetails(dto.getConditionDetails());

        dto.setStatus(projection.getStatus());

        dto.setCreatedAt(projection.getCreatedAt());
        dto.setUpdatedAt(projection.getUpdatedAt());
        dto.setCategory(projection.getCategoryDTO());
        dto.setSeller(projection.getSellerDTO());
        dto.setImages(projection.getImagesDTO());

        return dto;
    }
}
