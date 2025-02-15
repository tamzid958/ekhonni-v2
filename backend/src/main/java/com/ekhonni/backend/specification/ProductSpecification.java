/**
 * Author: Rifat Shariar Sakil
 * Time: 11:24 AM
 * Date: 12/22/2024
 * Project Name: backend
 */

package com.ekhonni.backend.specification;


import com.ekhonni.backend.enums.Division;
import com.ekhonni.backend.enums.ProductCondition;
import com.ekhonni.backend.enums.ProductStatus;
import com.ekhonni.backend.model.Product;
import com.ekhonni.backend.model.ProductBoost;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
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
        return (product, cq, cb) -> cb.equal(product.get("seller").get("id"), userId);
    }

    public static Specification<Product> hasStatus(ProductStatus status) {
        return (product, cq, cb) -> cb.equal(product.get("status"), status);
    }

    public static Specification<Product> belongsToDivision(Division division) {
        return (product, cq, cb) -> cb.equal(product.get("division"), division);
    }

    public static Specification<Product> isBoosted(Boolean applyBoost) {
        return (product, cq, cb) -> {
            if (Boolean.TRUE.equals(applyBoost)) {
                assert cq != null;
                Subquery<Long> subquery = cq.subquery(Long.class);
                Root<ProductBoost> productBoostRoot = subquery.from(ProductBoost.class);
                subquery.select(productBoostRoot.get("product").get("id"))
                        .where(cb.equal(productBoostRoot.get("product").get("id"), product.get("id")));
                return cb.exists(subquery);
            }
            return cb.conjunction();
        };
    }


    // searching
    public static Specification<Product> hasTerm(String searchTerm) {
        return (product, cq, cb) -> {
            String searchTermLower = "%" + searchTerm.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(product.get("title")), searchTermLower),
                    cb.like(cb.lower(product.get("subTitle")), searchTermLower),
                    cb.like(cb.lower(product.get("category").get("name")), searchTermLower),
                    cb.like(cb.lower(product.get("description")), searchTermLower)
            );
        };
    }


    // default specification (returns all products without any filters)
    public static Specification<Product> defaultSpec() {
        return (product, cq, cb) -> cb.conjunction();
    }
}
