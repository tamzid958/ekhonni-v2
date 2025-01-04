/**
 * Author: Rifat Shariar Sakil
 * Time: 11:24 AM
 * Date: 12/22/2024
 * Project Name: backend
 */

package com.ekhonni.backend.specification;


import com.ekhonni.backend.model.Product;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductSpecification {
    public static Specification<Product> hasMinimumPrice(Double minPrice) {
        return (product, cq, cb) -> cb.greaterThanOrEqualTo(product.get("price"), minPrice);
    }

    public static Specification<Product> hasMaximumPrice(Double maxPrice) {
        return (product, cq, cb) -> cb.lessThanOrEqualTo(product.get("price"), maxPrice);
    }

    public static Specification<Product> belongsToCategories(List<Long> categoryIds) {
        return (product, cq, cb) -> product.join("category").get("id").in(categoryIds);
    }
}
