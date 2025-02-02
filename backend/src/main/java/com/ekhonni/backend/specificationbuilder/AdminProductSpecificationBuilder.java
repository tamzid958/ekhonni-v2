/**
 * Author: Rifat Shariar Sakil
 * Time: 5:37 PM
 * Date: 1/30/25
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.specificationbuilder;


import com.ekhonni.backend.filter.AdminProductFilter;
import com.ekhonni.backend.model.Product;
import com.ekhonni.backend.specification.ProductSpecification;
import com.ekhonni.backend.specification.SpecificationResult;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class AdminProductSpecificationBuilder {
    public static Specification<Product> build(AdminProductFilter filter, List<Long> categoryIds) {
        SpecificationResult result = CommonProductSpecificationBuilder.build(filter, categoryIds);
        Specification<Product> spec = result.getSpec();
        boolean hasConditions = result.isHasConditions();

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
