/**
 * Author: Rifat Shariar Sakil
 * Time: 12:25 PM
 * Date: 2/9/2025
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.specificationbuilder;

import com.ekhonni.backend.filter.SellerProductFilter;
import com.ekhonni.backend.model.Product;
import com.ekhonni.backend.specification.ProductSpecification;
import com.ekhonni.backend.specification.SpecificationResult;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class SellerProductSpecificationBuilder {
    public static SpecificationResult build(SellerProductFilter filter, List<Long> categoryIds) {
        SpecificationResult result = CommonProductSpecificationBuilder.build(filter, categoryIds);
        Specification<Product> spec = result.getSpec();
        boolean hasConditions = result.isHasConditions();

        if (filter.getUserId() != null) {
            spec = spec.and(ProductSpecification.belongsToUser(filter.getUserId()));
            hasConditions = true;
        }


        if (!hasConditions) {
            spec = spec.and(ProductSpecification.defaultSpec());
        }
        return new SpecificationResult(spec, hasConditions);
    }
}