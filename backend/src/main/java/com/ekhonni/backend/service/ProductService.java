/**
 * Author: Rifat Shariar Sakil
 * Time: 2:25 PM
 * Date: 12/3/2024
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.service;


import com.ekhonni.backend.dto.ProductDTO;
import com.ekhonni.backend.enums.ProductSort;
import com.ekhonni.backend.exception.ProductNotFoundException;
import com.ekhonni.backend.filter.ProductFilter;
import com.ekhonni.backend.model.Category;
import com.ekhonni.backend.model.Product;
import com.ekhonni.backend.model.User;
import com.ekhonni.backend.projection.ProductProjection;
import com.ekhonni.backend.repository.CategoryRepository;
import com.ekhonni.backend.repository.ProductRepository;
import com.ekhonni.backend.specificationbuilder.ProductSpecificationBuilder;
import com.ekhonni.backend.util.AuthUtil;
import com.ekhonni.backend.util.ImageUploadUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.UUID;

@Service
public class ProductService extends BaseService<Product, Long> {
    ProductRepository productRepository;
    CategoryService categoryService;
    CategoryRepository categoryRepository;

    @Value("${upload.dir}")
    String UPLOAD_DIR;


    public ProductService(ProductRepository productRepository, CategoryService categoryService, CategoryRepository categoryRepository) {
        super(productRepository);
        this.productRepository = productRepository;
        this.categoryService = categoryService;
        this.categoryRepository = categoryRepository;
    }


    @Transactional
    public void create(ProductDTO productDTO) {

        try {
            User user = AuthUtil.getAuthenticatedUser();
            Category category = categoryRepository.findByName(productDTO.category());
            List<String> imagePaths = ImageUploadUtil.saveImage(UPLOAD_DIR, productDTO.images());


            Product product = new Product(
                    productDTO.name(),
                    productDTO.price(),
                    productDTO.description(),
                    false,
                    false,
                    productDTO.condition(),
                    category,
                    user,
                    imagePaths
            );

            System.out.println(product);
            productRepository.save(product);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }


    @Transactional
    public boolean approveProduct(Long id) {
        productRepository.findById(id).ifPresent(product -> {
            product.setApproved(true);
            productRepository.save(product);
        });
        return productRepository.existsById(id);
    }


    public boolean declineProduct(Long id) {
        productRepository.findById(id).ifPresent(product -> {
            // notify seller
        });
        return true;
    }


    public Page<ProductProjection> getAllFiltered(ProductFilter filter) {
        List<Long> categoryIds = categoryService.getActiveCategoryIds(filter.getCategoryName());
        Specification<Product> spec = ProductSpecificationBuilder.build(filter, categoryIds);
        Pageable pageable = PageRequest.of(filter.getPage(),filter.getSize());


        return productRepository.findAllFiltered(spec,pageable);
    }



    public UUID getSellerId(Long id) {
        return productRepository.findSellerIdById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
    }
}
