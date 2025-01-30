/**
 * Author: Rifat Shariar Sakil
 * Time: 5:37 PM
 * Date: 1/30/25
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.specificationbuilder;



import com.ekhonni.backend.enums.ProductStatus;
import com.ekhonni.backend.filter.AdminProductFilter;
import com.ekhonni.backend.model.Product;
import com.ekhonni.backend.specification.ProductSpecification;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class AdminProductSpecificationBuilder {
    public static Specification<Product> build(AdminProductFilter filter, List<Long> categoryIds) {

        Specification<Product> spec = Specification.where(null);
        boolean hasConditions = false;

        if (filter.getCategoryName() != null) {
            spec = spec.and(ProductSpecification.belongsToCategories(categoryIds));
            hasConditions = true;
        }
        if (filter.getMinPrice() != null) {
            spec = spec.and(ProductSpecification.hasMinimumPrice(filter.getMinPrice()));
            hasConditions = true;
        }
        if (filter.getMaxPrice() != null) {
            spec = spec.and(ProductSpecification.hasMaximumPrice(filter.getMaxPrice()));
            hasConditions = true;
        }
        if (filter.getCondition() != null) {
            spec = spec.and(ProductSpecification.hasCondition(filter.getCondition()));
            hasConditions = true;
        }
        if (filter.getSearchTerm() != null) {
            spec = spec.and(ProductSpecification.hasTerm(filter.getSearchTerm()));
        }
        if (filter.getSortBy() != null) {
            spec = spec.and(ProductSpecification.applySorting(filter.getSortBy()));
            hasConditions = true;
        }


        if (filter.getProductStatus() != null) {
            spec = spec.and(ProductSpecification.hasStatus(filter.getProductStatus()));
            hasConditions = true;
        }

        if (!hasConditions) {
            spec = spec.and(ProductSpecification.defaultSpec());
        }
        return spec;
    }
}
