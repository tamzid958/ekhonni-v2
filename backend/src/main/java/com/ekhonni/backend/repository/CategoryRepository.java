/**
 * Author: Rifat Shariar Sakil
 * Time: 8:45 PM
 * Date: 12/3/2024
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.repository;

import com.ekhonni.backend.model.Category;
import com.ekhonni.backend.projection.CategoryProjection;
import com.ekhonni.backend.projection.category.ViewerCategoryProjection;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CategoryRepository extends BaseRepository<Category, Long> {

    List<Category> findByParentCategoryIsNullAndActive(boolean active);
    List<CategoryProjection>findProjectionByParentCategoryIsNullAndActive(boolean active);
    List<ViewerCategoryProjection> findByParentCategoryAndActiveOrderByIdAsc(Category category, boolean active);





    @Modifying
    @Transactional
    @Query("DELETE FROM Category c WHERE c.id = :id")
    void deleteCategoryById(@Param("id") Long id);


    Category findByNameAndActive(String name, boolean active);

    Category findByName(String name);


    @Query(value = """
            WITH RECURSIVE category_tree AS (
                SELECT id
                FROM category
                WHERE id = :parentId AND active = true
                UNION ALL
                SELECT c.id
                FROM category c
                INNER JOIN category_tree ct
                ON c.parent_category_id = ct.id
                WHERE c.active = true
            )
            SELECT id FROM category_tree
            """, nativeQuery = true)
    List<Long> findRelatedActiveIds(Long parentId);


    List<Category> findByParentCategory(Category category);

    boolean existsByName(String name);

    boolean existsByParentCategoryName(String name);

    void deleteCategoryByName(String name);

    List<ViewerCategoryProjection> findByParentCategoryNameAndActiveOrderByIdAsc(String name, boolean b);


    @Query(value = """
    WITH RECURSIVE category_tree AS (
        SELECT id, id AS main_category_id
        FROM category
        WHERE id IN (:mainCategoryIds) AND active = true
        UNION ALL
        SELECT c.id, ct.main_category_id
        FROM category c
        INNER JOIN category_tree ct ON c.parent_category_id = ct.id
        WHERE c.active = true
    )
    SELECT ct.main_category_id, COUNT(p.id) AS product_count
    FROM category_tree ct
    LEFT JOIN product p ON p.category_id = ct.id
    GROUP BY ct.main_category_id
    """, nativeQuery = true)
    List<Object[]> countProductsByCategoriesAndDescendants(@Param("mainCategoryIds") List<Long> mainCategoryIds);


}
