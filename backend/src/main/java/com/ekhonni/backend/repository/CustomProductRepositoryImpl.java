/**
 * Author: Rifat Shariar Sakil
 * Time: 6:17 PM
 * Date: 1/4/2025
 * Project Name: backend
 */

package com.ekhonni.backend.repository;

import com.ekhonni.backend.model.Product;
import com.ekhonni.backend.projection.ProductProjection;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public class CustomProductRepositoryImpl implements CustomProductRepository {

    @Autowired
    private EntityManager entityManager;

    @Override
    public Page<ProductProjection> findAllFiltered(Specification<Product> spec, Pageable pageable) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<ProductProjection> query = cb.createQuery(ProductProjection.class);
        Root<Product> root = query.from(Product.class);
        
        Predicate predicate = spec.toPredicate(root, query, cb);
        query.where(predicate);

        query.select(cb.construct(
                ProductProjection.class,
                root.get("id"),
                root.get("price"),
                root.get("name"),
                root.get("description"),
                root.get("createdAt"),
                root.get("updatedAt"),
                root.get("condition"),
                root.get("category").get("id"),
                root.get("category").get("name"),
                root.get("imagePaths")
        ));

        TypedQuery<ProductProjection> typedQuery = entityManager.createQuery(query);

        int page = Math.max(1,pageable.getPageNumber());
        int size = Math.max(1,pageable.getPageSize());

        int firstResult = (page-1) * size;
        typedQuery.setFirstResult(firstResult);
        typedQuery.setMaxResults(size);

        return new PageImpl<>(typedQuery.getResultList());

    }
}