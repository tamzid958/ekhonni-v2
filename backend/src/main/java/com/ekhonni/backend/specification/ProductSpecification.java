/**
 * Author: Rifat Shariar Sakil
 * Time: 11:24 AM
 * Date: 12/22/2024
 * Project Name: backend
 */

package com.ekhonni.backend.specification;


import com.ekhonni.backend.enums.ProductCondition;
import com.ekhonni.backend.model.Product;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;


public class ProductSpecification {
    public static Specification<Product> belongsToCategories(List<Long> categoryIds) {
        return (product, cq, cb) -> product.join("category").get("id").in(categoryIds);
    }

    public static Specification<Product> hasMinimumPrice(Double minPrice) {
        return (product, cq, cb) -> cb.greaterThanOrEqualTo(product.get("price"), minPrice);
    }

    public static Specification<Product> hasMaximumPrice(Double maxPrice) {
        return (product, cq, cb) -> cb.lessThanOrEqualTo(product.get("price"), maxPrice);
    }

    public static Specification<Product> hasCondition(ProductCondition condition) {
        return (product, cq, cb) -> cb.equal(product.get("condition"), condition);
    }

}
