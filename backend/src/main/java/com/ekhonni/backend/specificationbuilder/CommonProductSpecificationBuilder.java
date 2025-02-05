/**
 * Author: Rifat Shariar Sakil
 * Time: 3:18 PM
 * Date: 1/4/2025
 * Project Name: backend
 */

package com.ekhonni.backend.specificationbuilder;

import com.ekhonni.backend.filter.ProductFilter;
import com.ekhonni.backend.model.Product;
import com.ekhonni.backend.specification.ProductSpecification;
import com.ekhonni.backend.specification.SpecificationResult;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class CommonProductSpecificationBuilder {
    public static SpecificationResult build(ProductFilter filter, List<Long> categoryIds) {

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
        if(filter.getDivision() != null){
            spec = spec.and(ProductSpecification.belongsToDivision(filter.getDivision()));
        }
//        if (filter.getSortBy() != null) {
//            System.out.println("sort");
//            spec = spec.and(ProductSpecification.applySorting(filter.getSortBy()));
//            hasConditions = true;
//        }

        if (filter.getStatus() != null) {
            spec = spec.and(ProductSpecification.hasStatus(filter.getStatus()));
            hasConditions = true;
        }

        if(!hasConditions)
        {
            spec = spec.and(ProductSpecification.defaultSpec());
        }
        return new SpecificationResult(spec, hasConditions);
    }
}
