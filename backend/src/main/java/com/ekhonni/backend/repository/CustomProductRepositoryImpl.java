/**
 * Author: Rifat Shariar Sakil
 * Time: 6:17 PM
 * Date: 1/4/2025
 * Project Name: backend
 */

package com.ekhonni.backend.repository;

import com.ekhonni.backend.model.Product;
import com.ekhonni.backend.model.ProductImage;
import com.ekhonni.backend.projection.implementation.ProductProjectionImpl;
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
    public Page<ProductProjectionImpl> findAllFiltered(Specification<Product> spec, Pageable pageable) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<ProductProjectionImpl> query = cb.createQuery(ProductProjectionImpl.class);
        Root<Product> root = query.from(Product.class);


        Predicate predicate = spec.toPredicate(root, query, cb);
        query.where(predicate);

        query.select(cb.construct(
                ProductProjectionImpl.class,
                root.get("id"),
                root.get("price"),
                root.get("name"),
                root.get("description"),
                root.get("createdAt"),
                root.get("updatedAt"),
                root.get("condition"),
                root.get("category").get("id"),
                root.get("category").get("name")

        ));


        TypedQuery<ProductProjectionImpl> typedQuery = entityManager.createQuery(query);

        int page = Math.max(1,pageable.getPageNumber());
        int size = Math.max(1,pageable.getPageSize());

        int firstResult = (page-1) * size;
        typedQuery.setFirstResult(firstResult);
        typedQuery.setMaxResults(size);
        List<ProductProjectionImpl> products = typedQuery.getResultList();


        // fetch images of fetched products
        for (ProductProjectionImpl product : products) {
            Long productId = product.getId();
            List<ProductImage> images = getImagesOfProduct(productId);
            product.setImages(images);
        }

        // totalElements
        long total = 0;
        return new PageImpl<>(products,pageable,total);
    }


    private List<ProductImage>getImagesOfProduct(Long productId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<ProductImage> query = cb.createQuery(ProductImage.class);
        Root<Product> root = query.from(Product.class);


        Join<Product, ProductImage> imageJoin = root.join("images", JoinType.LEFT);

        query.select(imageJoin)
                .where(cb.equal(root.get("id"), productId));

        TypedQuery<ProductImage> typedQuery = entityManager.createQuery(query);
        return typedQuery.getResultList();

    }




}