/**
 * Author: Rifat Shariar Sakil
 * Time: 2:26 PM
 * Date: 12/3/2024
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.repository;

import com.ekhonni.backend.model.Product;
import com.ekhonni.backend.projection.ProductProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product>, BaseRepository<Product, Long> {


    Page<ProductProjection> findAllByCategoriesName(String categoryName, Pageable pageable);


    @Query("SELECT p.id AS id, p.price AS price, p.name AS name, p.description AS description, " +
            "p.createdAt AS createdAt, p.updatedAt AS updatedAt, p.condition AS condition, " +
            "p.category.id AS categoryId, p.category.name AS categoryName " +
            "FROM Product p " +
            "WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
            "LOWER(p.description) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
            "LOWER(p.category.name) LIKE LOWER(CONCAT('%', :searchText, '%'))")
    Page<ProductProjection> searchProducts(String searchText, Pageable pageable);


}
