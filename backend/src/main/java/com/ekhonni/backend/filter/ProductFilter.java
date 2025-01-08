/**
 * Author: Rifat Shariar Sakil
 * Time: 12:14 AM
 * Date: 1/1/2025
 * Project Name: backend
 */

package com.ekhonni.backend.filter;

import com.ekhonni.backend.enums.ProductCondition;
import com.ekhonni.backend.enums.ProductSort;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class ProductFilter {
    private String categoryName;
    private Double minPrice;
    private Double maxPrice;
    private ProductCondition condition;
    private String searchTerm;
    private ProductSort sortBy;
    private int page;
    private int size;
}
