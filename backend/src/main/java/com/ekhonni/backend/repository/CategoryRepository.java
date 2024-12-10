/**
 * Author: Rifat Shariar Sakil
 * Time: 8:45 PM
 * Date: 12/3/2024
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.repository;

import com.ekhonni.backend.model.Category;
import com.ekhonni.backend.projection.CategoryProjection;
import com.ekhonni.backend.projection.ProductProjection;
import org.hibernate.query.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {


    @Query("SELECT c.id AS id, c.name AS name, c.active AS active, c.subCategories AS subCategories " +
            "FROM Category c WHERE c.parentCategory IS NULL")
    List<CategoryProjection> findAllProjection();


    CategoryProjection findCategoryProjectionById(Long id);
}
