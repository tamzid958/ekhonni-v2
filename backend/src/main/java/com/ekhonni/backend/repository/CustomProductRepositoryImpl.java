/// **
// * Author: Rifat Shariar Sakil
// * Time: 12:16 AM
// * Date: 1/1/2025
// * Project Name: backend
// */
//
//package com.ekhonni.backend.repository;
//
//import com.ekhonni.backend.projection.ProductProjection;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.Query;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//public class CustomProductRepositoryImpl implements CustomProductRepository {
//
//    @Autowired
//    private EntityManager entityManager;
//
//    @Override
//    public List<ProductProjection> findAllFilteredProducts(Long categoryId) {
//        String nativeQuery = """
//                    WITH RECURSIVE category_tree AS (
//                        SELECT id, parent_category_id
//                        FROM category
//                        WHERE id = :categoryId
//                        UNION ALL
//                        SELECT c.id, c.parent_category_id
//                        FROM category c
//                        INNER JOIN category_tree ct ON c.parent_category_id = ct.id
//                    )
//                    SELECT p.id, p.price, p.name, p.description,
//                           p.created_at, p.updated_at, p.condition,
//                           c.id AS category_id, c.name AS category_name
//                    FROM product p
//                    JOIN category_tree ct ON p.category_id = ct.id
//                    JOIN category c ON p.category_id = c.id
//                """;
//
//        Query query = entityManager.createNativeQuery(nativeQuery, ProductProjection.class);
//        query.setParameter("categoryId", categoryId);
//
//        return query.getResultList();
//    }
//}
