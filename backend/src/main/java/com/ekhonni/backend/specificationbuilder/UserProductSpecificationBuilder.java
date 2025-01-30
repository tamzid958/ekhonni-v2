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
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class UserProductSpecificationBuilder {
    public static Specification<Product> build(UserProductFilter filter, List<Long> categoryIds) {
        Specification<Product> spec = CommonProductSpecificationBuilder.build(filter, categoryIds);
        if (filter.getUserId() != null) {
            spec = spec.and(ProductSpecification.belongsToUser(filter.getUserId()));
        }
        return spec;
    }
}
