/**
 * Author: Rifat Shariar Sakil
 * Time: 8:55 PM
 * Date: 1/20/25
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.specificationbuilder;


import com.ekhonni.backend.filter.ProductFilter;
import com.ekhonni.backend.filter.UserProductFilter;
import com.ekhonni.backend.model.Product;
import com.ekhonni.backend.specification.ProductSpecification;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class UserProductSpecificationBuilder {
    public static Specification<Product> build(UserProductFilter filter, List<Long> categoryIds) {

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

        if(filter.getUserId() != null){
            spec = spec.and(ProductSpecification.belongsToUser(filter.getUserId()));
            hasConditions = true;
        }



        if(!hasConditions)
        {
            spec = spec.and(ProductSpecification.defaultSpec());
        }
        return spec;
    }
}
