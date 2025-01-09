/**
 * Author: Rifat Shariar Sakil
 * Time: 6:17 PM
 * Date: 1/4/2025
 * Project Name: backend
 */

package com.ekhonni.backend.repository.Implementation;

import com.ekhonni.backend.dto.ProductResponseDTO;
import com.ekhonni.backend.model.Product;
//import com.ekhonni.backend.projection.implementation.ProductProjectionImpl;
import com.ekhonni.backend.model.ProductImage;
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
    public List<Long> findAllFiltered(Specification<Product> spec,Pageable pageable) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<Product> root = query.from(Product.class);


        Predicate predicate = spec.toPredicate(root, query, cb);
        query.where(predicate);


        query.select(root.get("id")).where(predicate);


        int page = Math.max(1, pageable.getPageNumber());
        int size = Math.max(1, pageable.getPageSize());

        int firstResult = (page - 1) * size;
        TypedQuery<Long> idQuery = entityManager.createQuery(query);
        idQuery.setFirstResult(firstResult);
        idQuery.setMaxResults(size);


        List<Long> productIds = idQuery.getResultList();


        return productIds;
    }





}