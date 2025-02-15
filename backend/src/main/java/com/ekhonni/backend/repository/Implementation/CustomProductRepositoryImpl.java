/**
 * Author: Rifat Shariar Sakil
 * Time: 6:17 PM
 * Date: 1/4/2025
 * Project Name: backend
 */

package com.ekhonni.backend.repository.Implementation;

import com.ekhonni.backend.model.Product;
import com.ekhonni.backend.model.ProductBoost;
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

import java.util.ArrayList;
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

        // Subquery to check if a product is boosted
        Subquery<Long> subquery = query.subquery(Long.class);
        Root<ProductBoost> productBoostRoot = subquery.from(ProductBoost.class);
        subquery.select(productBoostRoot.get("product").get("id"))
                .where(cb.equal(productBoostRoot.get("product").get("id"), root.get("id")));

        // Sorting: Boosted products first
        List<Order> orderList = new ArrayList<>();
        orderList.add(cb.desc(cb.exists(subquery))); // Boosted products first

        // Apply sorting from Pageable
        if (pageable.getSort().isSorted()) {
            pageable.getSort().forEach(order -> {
                if (order.isAscending()) {
                    orderList.add(cb.asc(root.get(order.getProperty())));
                } else {
                    orderList.add(cb.desc(root.get(order.getProperty())));
                }
            });
        }

        System.out.println(orderList);
        System.out.println(orderList.size());
        query.orderBy(orderList);

        int page = Math.max(0, pageable.getPageNumber());
        int size = Math.max(5, pageable.getPageSize());
        int firstResult = page * size;

        TypedQuery<Long> idQuery = entityManager.createQuery(query);
        idQuery.setFirstResult(firstResult);
        idQuery.setMaxResults(size);

        List<Long> productIds = idQuery.getResultList();

        // Query to count total elements matching the specification
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Product> countRoot = countQuery.from(Product.class);
        Predicate countPredicate = spec.toPredicate(countRoot, countQuery, cb);
        if (countPredicate != null) {
            countQuery.where(countPredicate);
        }

        countQuery.select(cb.count(countRoot));
        // countQuery.select(cb.count(countRoot)).where(countPredicate);

        Long totalElements = entityManager.createQuery(countQuery).getSingleResult();

        System.out.println(productIds);

        return new PageImpl<>(productIds, pageable, totalElements);
    }
}