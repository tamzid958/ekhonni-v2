/**
 * Author: Rifat Shariar Sakil
 * Time: 5:23 PM
 * Date: 1/4/2025
 * Project Name: backend
 */

package com.ekhonni.backend.repository;

import com.ekhonni.backend.model.Product;
import com.ekhonni.backend.projection.ProductProjection;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CustomProductRepository {

    List<ProductProjection> findAllFiltered(Specification<Product> spec);

}
