/**
 * Author: Rifat Shariar Sakil
 * Time: 11:50 PM
 * Date: 1/12/2025
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.service;

import com.ekhonni.backend.dto.product.ProductResponseDTO;
import com.ekhonni.backend.enums.ProductStatus;
import com.ekhonni.backend.exception.ProductNotFoundException;
import com.ekhonni.backend.filter.AdminProductFilter;
import com.ekhonni.backend.model.Product;
import com.ekhonni.backend.projection.ProductProjection;
import com.ekhonni.backend.repository.ProductRepository;
import com.ekhonni.backend.specificationbuilder.AdminProductSpecificationBuilder;
import com.ekhonni.backend.util.ProductProjectionConverter;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductAdminService {

    ProductRepository productRepository;
    CategoryService categoryService;
    NotificationService notificationService;

    public ProductAdminService(ProductRepository productRepository, CategoryService categoryService, NotificationService notificationService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
        this.notificationService = notificationService;
    }


    public Page<ProductProjection> getAll(Pageable pageable) {
        return productRepository.findAllByStatus(ProductStatus.PENDING_APPROVAL, pageable);
    }

    public ProductProjection getOne(Long id) {
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
        // add approvedBy
        //product.setApprovedBy();
        productRepository.save(product);

        // should we work with projection or product?
        // notify seller
        notificationService.createForProductAccepted(product);

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
        notificationService.createForProductRejected(product);

        return "Post declined";

    }

    @Transactional
    public String deleteOne(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product doesn't exist"));

        if (product.getStatus() != ProductStatus.APPROVED) {
            throw new IllegalStateException("Product is not for archive");
        }
        product.setStatus(ProductStatus.ARCHIVED);
        productRepository.save(product);

        // should we work with projection or product
        // notify seller.
        notificationService.createForProductDeleted(product);

        return "Post Archived";
    }


    public Page<ProductResponseDTO> getAllFilteredForAdmin(AdminProductFilter filter) {
        List<Long> categoryIds = new ArrayList<>();
        if (filter.getCategoryName() != null && !filter.getCategoryName().isEmpty()) {
            categoryIds = categoryService.getRelatedActiveIds(filter.getCategoryName());
        }


        Specification<Product> spec = AdminProductSpecificationBuilder.build(filter, categoryIds);
        Pageable pageable = PageRequest.of(filter.getPage()-1, filter.getSize());
        Page<Long> page = productRepository.findAllFiltered(spec, pageable);
        List<ProductProjection> projections = productRepository.findByIdIn(page.getContent(),pageable);
        List<ProductResponseDTO> products = projections.stream()
                .map(ProductProjectionConverter::convert)
                .toList();
        long totalElements = page.getTotalElements();
        return new PageImpl<>(products, pageable, totalElements);
    }
}
