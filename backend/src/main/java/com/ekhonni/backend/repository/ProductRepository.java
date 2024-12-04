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
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p where p.categoryId=?1 AND p.productApproved=false")
    List<Product> findAllProductByCategoryId(Long Id);
}
