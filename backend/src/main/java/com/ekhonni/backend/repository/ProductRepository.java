/**
 * Author: Rifat Shariar Sakil
 * Time: 2:26 PM
 * Date: 12/3/2024
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.repository;

import com.ekhonni.backend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

//    @Query("SELECT p FROM Product p where p.parentCategoryId=?1 AND p.productApproved=false")
//    List<Product> findAllProductByCategoryId(Long Id);

    List<Product> findByCategoryId(Long categoryId);

    @Query("SELECT p FROM Product p WHERE p.category.id = :categoryId OR p.category.parentCategory.id = :categoryId")
    List<Product> findAllProductsByCategoryAndSubCategories(Long categoryId);


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
    List<Product> findAllProductsInCategoryTree(Long categoryId);
}
