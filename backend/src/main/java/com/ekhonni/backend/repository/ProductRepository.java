/**
 * Author: Rifat Shariar Sakil
 * Time: 2:26 PM
 * Date: 12/3/2024
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.repository;

import com.ekhonni.backend.model.Product;
import com.ekhonni.backend.projection.CategoryProjection;
import com.ekhonni.backend.projection.ProductProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {



    @Query("SELECT p.id AS id, p.price AS price, p.name AS name, p.description AS description, " +
            "p.createdAt AS createdAt, p.updatedAt AS updatedAt, p.condition AS condition, p.category.id AS categoryId, p.category.name AS categoryName " +
            "FROM Product p")
    Page<ProductProjection> findAllProjection(Pageable pageable);



    ProductProjection findProductProjectionById(Long id);



    @Query(value = """
    WITH RECURSIVE category_tree AS (
        SELECT id, parent_category_id
        FROM category
        WHERE id = :categoryId
        UNION ALL
        SELECT c.id, c.parent_category_id
        FROM category c
        INNER JOIN category_tree ct ON c.parent_category_id = ct.id
    )
    SELECT p.*,
           c.id AS categoryId,
           c.name AS categoryName
    FROM product p
    JOIN category_tree ct ON p.category_id = ct.id
    JOIN category c ON p.category_id = c.id
    """,
            countQuery = """
    WITH RECURSIVE category_tree AS (
        SELECT id, parent_category_id
        FROM category
        WHERE id = :categoryId
        UNION ALL
        SELECT c.id, c.parent_category_id
        FROM category c
        INNER JOIN category_tree ct ON c.parent_category_id = ct.id
    )
    SELECT COUNT(*)
    FROM product p
    JOIN category_tree ct ON p.category_id = ct.id
    """, nativeQuery = true)
    Page<ProductProjection> findAllProjectionByCategoryId(@Param("categoryId") Long categoryId, Pageable pageable);

}
