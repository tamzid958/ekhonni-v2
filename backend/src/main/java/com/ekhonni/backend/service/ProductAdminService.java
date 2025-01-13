/**
 * Author: Rifat Shariar Sakil
 * Time: 11:50 PM
 * Date: 1/12/2025
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.service;

import com.ekhonni.backend.enums.ProductStatus;
import com.ekhonni.backend.exception.ProductNotFoundException;
import com.ekhonni.backend.model.Product;
import com.ekhonni.backend.projection.ProductProjection;
import com.ekhonni.backend.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductAdminService {

    ProductRepository productRepository;

    public ProductAdminService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

//    public deleteOne(Long id) {
//    }

    public Page<ProductProjection> getAllPending(Pageable pageable) {
        Page<ProductProjection> projections = productRepository.findAllByStatus(ProductStatus.PENDING_APPROVAL, pageable);
        return projections;
    }

    public ProductProjection getOnePending(Long id) {
        ProductProjection projection = productRepository.findProjectionById(id);
        if (projection == null) throw new ProductNotFoundException("product doesn't exist");
        return projection;
    }

    @Transactional
    public String approveOne(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product doesn't exist"));

        if (product.getStatus() != ProductStatus.PENDING_APPROVAL) {
            throw new IllegalStateException("Product is not pending approval");
        }

        product.setStatus(ProductStatus.APPROVED);
        productRepository.save(product);

        // should we work with projection or product?
        // notify seller

        return "Product approved successfully";
    }

    @Transactional
    public String declineOne(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product doesn't exist"));

        if (product.getStatus() != ProductStatus.PENDING_APPROVAL) {
            throw new IllegalStateException("Product is not pending approval");
        }

        product.setStatus(ProductStatus.DECLINED);
        productRepository.save(product);

        // should we work with projection or product?
        // notify seller

        return "Post declined";

    }
}
