/**
 * Author: Rifat Shariar Sakil
 * Time: 2:26 PM
 * Date: 12/3/2024
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.repository;

import com.ekhonni.backend.enums.ProductStatus;
import com.ekhonni.backend.model.Product;
import com.ekhonni.backend.projection.ProductProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, CustomProductRepository, JpaSpecificationExecutor<Product>, BaseRepository<Product, Long> {

    ProductProjection findProjectionById(Long id);

    List<ProductProjection> findByIdIn(List<Long> productIds);

    //admin site
    Page<ProductProjection> findAllByStatus(ProductStatus status, Pageable pageable);

}
