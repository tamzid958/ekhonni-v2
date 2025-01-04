/**
 * Author: Rifat Shariar Sakil
 * Time: 3:18 PM
 * Date: 1/4/2025
 * Project Name: backend
 */

package com.ekhonni.backend.specificationbuilder;

import com.ekhonni.backend.enums.ProductCondition;
import com.ekhonni.backend.filter.ProductFilter;
import com.ekhonni.backend.model.Product;
import com.ekhonni.backend.specification.ProductSpecification;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class ProductSpecificationBuilder {
    public static Specification<Product> build(ProductFilter filter, List<Long> categoryIds) {
        Specification<Product> spec = Specification.where(null);
        if (filter.getMinPrice() != null) {
            spec = spec.and(ProductSpecification.hasMinimumPrice(filter.getMinPrice()));
        }
        if (filter.getMaxPrice() != null) {
            spec = spec.and(ProductSpecification.hasMaximumPrice(filter.getMaxPrice()));
        }
        if (categoryIds != null && filter.getCategoryName() != null) {
            spec = spec.and(ProductSpecification.belongsToCategories(categoryIds));
        }
        if (filter.getProductCondition() != null) {
            spec = spec.and(ProductSpecification.hasCondition(ProductCondition.valueOf(filter.getProductCondition())));
        }
        return spec;
    }
}
