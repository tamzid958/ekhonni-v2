/**
 * Author: Rifat Shariar Sakil
 * Time: 2:26 PM
 * Date: 12/3/2024
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.repository;

import com.ekhonni.backend.dto.ProductResponseDTO;
import com.ekhonni.backend.model.Product;
import com.ekhonni.backend.projection.ProductProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, CustomProductRepository, JpaSpecificationExecutor<Product>, BaseRepository<Product, Long> {

    ProductProjection findProjectionById(Long id);
}
