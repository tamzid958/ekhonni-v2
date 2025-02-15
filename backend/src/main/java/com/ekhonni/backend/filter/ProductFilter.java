/**
 * Author: Rifat Shariar Sakil
 * Time: 12:14 AM
 * Date: 1/1/2025
 * Project Name: backend
 */

package com.ekhonni.backend.filter;

import com.ekhonni.backend.enums.Division;
import com.ekhonni.backend.enums.ProductCondition;
import com.ekhonni.backend.enums.ProductSort;
import com.ekhonni.backend.enums.ProductStatus;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class ProductFilter {
    private final ProductStatus status = ProductStatus.APPROVED;
    private String categoryName;
    private Double minPrice;
    private Double maxPrice;
    private ProductCondition condition;
    private String searchTerm;
    private ProductSort sortBy;
    private Division division;
    private Boolean applyBoosted;
    private int page = 1;
    private int size = 10;
}
