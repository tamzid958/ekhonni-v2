/**
 * Author: Rifat Shariar Sakil
 * Time: 2:26 PM
 * Date: 12/3/2024
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.repository;

import com.ekhonni.backend.model.Product;
import com.ekhonni.backend.projection.ProductProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p.id AS id, p.price AS price, p.name AS name, p.description AS description, " +
            "p.createdAt AS createdAt, p.updatedAt AS updatedAt, p.condition AS condition, p.category AS category " +
            "FROM Product p")
    List<ProductProjection> findAllProjected();

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
        SELECT p.*
        FROM product p
        JOIN category_tree ct ON p.category_id = ct.id
    """, nativeQuery = true)
    List<ProductProjection> findAllByCategoryId(Long categoryId);




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
    SELECT p.*
    FROM product p
    JOIN category_tree ct ON p.category_id = ct.id
    WHERE p.approved = true;
""", nativeQuery = true)
    List<Product> findAllApprovedProductsInCategoryTree(Long categoryId);


//    @Query(value = "SELECT * FROM product WHERE approved = true", nativeQuery = true)
//    List<Product> findApprovedProducts();

}
