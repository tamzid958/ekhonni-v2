/**
 * Author: Rifat Shariar Sakil
 * Time: 11:24 AM
 * Date: 12/22/2024
 * Project Name: backend
 */

package com.ekhonni.backend.specification;


import com.ekhonni.backend.enums.ProductCondition;
import com.ekhonni.backend.enums.ProductSort;
import com.ekhonni.backend.model.Product;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.UUID;


public class ProductSpecification {

    // filtering
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

    public static Specification<Product> belongsToUser(UUID userId) {
        return (product, cq, cb) -> cb.equal(product.get("sellernoteno").get("id"), userId);
    }





    // sorting
    public static Specification<Product> applySorting(ProductSort sortBy) {
        return (product, cq, cb) -> {
            switch (sortBy) {
                case priceLowToHigh:
                    cq.orderBy(cb.asc(product.get("price")));
                    break;
                case priceHighToLow:
                    cq.orderBy(cb.desc(product.get("price")));
                    break;
                case newlyListed:
                    cq.orderBy(cb.desc(product.get("createdAt")));
            }
            return cb.conjunction();
        };
    }

    // searching
    public static Specification<Product> hasTerm(String searchTerm) {
        return (product, cq, cb) -> {
            String searchTermLower = "%" + searchTerm.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(product.get("name")), searchTermLower),
                    cb.like(cb.lower(product.get("category").get("name")), searchTermLower),
                    cb.like(cb.lower(product.get("description")), searchTermLower)
            );
        };
    }


    // default specification (returns all products without any filters)
    public static Specification<Product> defaultSpec() {
        return (product, cq, cb) -> cb.conjunction();  // Returns all products (no filter applied)
    }
}
