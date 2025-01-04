/**
 * Author: Rifat Shariar Sakil
 * Time: 12:14 AM
 * Date: 1/1/2025
 * Project Name: backend
 */

package com.ekhonni.backend.filter;

import com.ekhonni.backend.enums.ProductSort;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class ProductFilter {
    private String categoryName;
    private Double minPrice;
    private Double maxPrice;
    private String productCondition;
    private String searchTerm;
    private ProductSort sortBy;

}
