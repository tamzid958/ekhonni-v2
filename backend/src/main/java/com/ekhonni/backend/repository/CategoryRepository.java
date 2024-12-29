/**
 * Author: Rifat Shariar Sakil
 * Time: 8:45 PM
 * Date: 12/3/2024
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.repository;

import com.ekhonni.backend.model.Category;
import com.ekhonni.backend.projection.CategoryProjection;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CategoryRepository extends BaseRepository<Category, Long> {

    List<Category> findByParentCategoryIsNull();

    List<CategoryProjection> findByParentCategoryOrderByIdAsc(Category category);

    List<CategoryProjection> findAllByParentCategory(Category parentCategory);

    List<CategoryProjection> findAllByParentCategoryIsNull();


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
