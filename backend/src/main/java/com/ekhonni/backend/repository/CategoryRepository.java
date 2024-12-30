/**
 * Author: Rifat Shariar Sakil
 * Time: 8:45 PM
 * Date: 12/3/2024
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.repository;

import com.ekhonni.backend.model.Category;
import com.ekhonni.backend.projection.CategoryProjection;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends BaseRepository<Category, Long> {

    List<Category> findByParentCategoryIsNullAndActive(boolean active);

    List<CategoryProjection> findByParentCategoryAndActiveOrderByIdAsc(Category category, boolean active);

    List<CategoryProjection> findAllByParentCategoryAndActive(Category parentCategory, boolean active);

    List<CategoryProjection> findAllByParentCategoryIsNullAndActive(boolean active);


    @Modifying
    @Transactional
    @Query(value = """
            WITH RECURSIVE category_tree AS (
                SELECT id FROM category WHERE parent_category_id = :parentId
                UNION ALL
                SELECT c.id FROM category c
                INNER JOIN category_tree ct ON c.parent_category_id = ct.id
            )
            DELETE FROM category WHERE id IN (SELECT id FROM category_tree) OR id = :parentId
            """, nativeQuery = true)
    void deleteCategoryById(Long parentId);


    Category findByName(String name);
}
