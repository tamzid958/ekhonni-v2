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
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, CustomProductRepository, JpaSpecificationExecutor<Product>, BaseRepository<Product, Long> {


    List<ProductProjection> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrCategoryNameContainingIgnoreCase(String text1, String text2, String text3);

    @Query("SELECT p.imagePaths FROM Product p WHERE p.id = :id")
    List<String> findImagePathsById(Long id);

    List<Product> findByCategoryIdIn(List<Long> categoryIds);


}
