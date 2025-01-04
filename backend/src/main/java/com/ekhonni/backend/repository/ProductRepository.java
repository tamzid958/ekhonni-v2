/**
 * Author: Rifat Shariar Sakil
 * Time: 2:26 PM
 * Date: 12/3/2024
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.repository;

import com.ekhonni.backend.filter.ProductFilter;
import com.ekhonni.backend.model.Product;
import com.ekhonni.backend.projection.ProductProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product>, BaseRepository<Product, Long> {

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
                SELECT p.id AS id, p.price AS price, p.name AS name, p.description AS description,
                       p.created_at AS createdAt, p.updated_at AS updatedAt,
                       p.condition AS condition,
                       c.id AS category_id, c.name AS category_name
                FROM product p
                JOIN category_tree ct ON p.category_id = ct.id
                JOIN category c ON p.category_id = c.id
                WHERE COALESCE(:#{#filter.maxPrice}, p.price) >= p.price
                  AND COALESCE(:#{#filter.minPrice}, p.price) <= p.price
                  AND COALESCE(:#{#filter.productCondition}, p.condition) = p.condition
            
                  ORDER BY
                       CASE
                         WHEN :#{#filter.sortBy.name()} = 'priceLowToHigh' THEN p.price
                         WHEN :#{#filter.sortBy.name()} = 'priceHighToLow' THEN -p.price
                       END ASC,
                       CASE
                         WHEN :#{#filter.sortBy.name()} = 'newlyListed' THEN p.created_at
                       END DESC
            """, nativeQuery = true)
    List<ProductProjection> findAllProjectionByFilter(@Param("filter") ProductFilter filter, @Param("categoryId") Long categoryId);

    List<ProductProjection> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrCategoryNameContainingIgnoreCase(String text1, String text2, String text3);

    @Query("SELECT p.imagePaths FROM Product p WHERE p.id = :id")
    List<String> findImagePathsById(Long id);


    List<Product> findByCategoryIdIn(List<Long> categoryIds);

}
