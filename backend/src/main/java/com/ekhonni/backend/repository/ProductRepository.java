/**
 * Author: Rifat Shariar Sakil
 * Time: 2:26 PM
 * Date: 12/3/2024
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.repository;

import com.ekhonni.backend.enums.ProductStatus;
import com.ekhonni.backend.model.Category;
import com.ekhonni.backend.model.Product;
import com.ekhonni.backend.projection.ProductProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, CustomProductRepository, JpaSpecificationExecutor<Product>, BaseRepository<Product, Long> {
    @Query("SELECT p.seller.id FROM Product p WHERE id = :id")
    Optional<UUID> findSellerIdById(Long id);

    ProductProjection findProjectionById(Long id);

    List<ProductProjection> findByIdIn(List<Long> productIds);


    //admin site
    Page<ProductProjection> findAllByStatus(ProductStatus status, Pageable pageable);

    @Query("SELECT DISTINCT p.category FROM Product p WHERE p.seller.id = :id and p.status=APPROVED")
    List<Category> findCategoriesBySeller(@Param("id") UUID id);


    Long countByIdInAndStatus(List<Long> ids, ProductStatus status);


}
