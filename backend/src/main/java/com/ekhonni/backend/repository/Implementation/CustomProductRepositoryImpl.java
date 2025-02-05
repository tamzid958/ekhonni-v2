/**
 * Author: Rifat Shariar Sakil
 * Time: 6:17 PM
 * Date: 1/4/2025
 * Project Name: backend
 */

package com.ekhonni.backend.repository.Implementation;

import com.ekhonni.backend.model.Product;
//import com.ekhonni.backend.projection.implementation.ProductProjectionImpl;
import com.ekhonni.backend.repository.CustomProductRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
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
    public Page<Long> findAllFiltered(Specification<Product> spec, Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        // Query to fetch product IDs for the current page
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<Product> root = query.from(Product.class);
        Predicate predicate = spec.toPredicate(root, query, cb);
        query.select(root.get("id")).where(predicate);

        // Apply sorting from Pageable
        if (pageable.getSort().isSorted()) {
            System.out.println(pageable.getSort());
            List<Order> orderList = pageable.getSort().stream()
                    .map(order -> order.isAscending() ? cb.asc(root.get(order.getProperty()))
                            : cb.desc(root.get(order.getProperty())))
                    .toList();
            query.orderBy(orderList);
        }

        int page = Math.max(0, pageable.getPageNumber());
        int size = Math.max(5, pageable.getPageSize());
        int firstResult = page  * size;

        TypedQuery<Long> idQuery = entityManager.createQuery(query);
        idQuery.setFirstResult(firstResult);
        idQuery.setMaxResults(size);

        List<Long> productIds = idQuery.getResultList();

        // Query to count total elements matching the specification
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Product> countRoot = countQuery.from(Product.class);
        Predicate countPredicate = spec.toPredicate(countRoot, countQuery, cb);
        countQuery.select(cb.count(countRoot)).where(countPredicate);

        Long totalElements = entityManager.createQuery(countQuery).getSingleResult();

        System.out.println(productIds);

        return new PageImpl<>(productIds, pageable, totalElements);
    }
}