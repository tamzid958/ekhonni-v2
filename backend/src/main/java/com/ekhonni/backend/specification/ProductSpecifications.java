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

@Component
public class ProductSpecifications {
    public static Specification<Product> hasMinimumPrice(Double minPrice) {
        return (product, cq, cb) -> cb.greaterThanOrEqualTo(product.get("price"), minPrice);
    }
}
