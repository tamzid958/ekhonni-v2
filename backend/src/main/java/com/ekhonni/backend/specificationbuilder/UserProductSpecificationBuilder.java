/**
 * Author: Rifat Shariar Sakil
 * Time: 8:55 PM
 * Date: 1/20/25
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.specificationbuilder;


import com.ekhonni.backend.filter.UserProductFilter;
import com.ekhonni.backend.model.Product;
import com.ekhonni.backend.specification.ProductSpecification;
import com.ekhonni.backend.specification.SpecificationResult;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class UserProductSpecificationBuilder {
    public static SpecificationResult build(UserProductFilter filter, List<Long> categoryIds) {
        SpecificationResult result = CommonProductSpecificationBuilder.build(filter, categoryIds);
        Specification<Product> spec = result.getSpec();
        boolean hasConditions = result.isHasConditions();

        if (filter.getUserId() != null) {
            spec = spec.and(ProductSpecification.belongsToUser(filter.getUserId()));
            hasConditions = true;
        }

        if (filter.getStatus() != null) {
            spec = spec.and(ProductSpecification.hasStatus(filter.getStatus()));
            hasConditions = true;
        }

        if (!hasConditions) {
            spec = spec.and(ProductSpecification.defaultSpec());
        }
        return new SpecificationResult(spec, hasConditions);
    }
}
