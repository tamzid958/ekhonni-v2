/**
 * Author: Rifat Shariar Sakil
 * Time: 2:25 PM
 * Date: 12/3/2024
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.service;


import com.ekhonni.backend.dto.ProductCreateDTO;
import com.ekhonni.backend.dto.ProductResponseDTO;
import com.ekhonni.backend.filter.ProductFilter;
import com.ekhonni.backend.model.*;
import com.ekhonni.backend.projection.ProductProjection;
import com.ekhonni.backend.repository.CategoryRepository;
import com.ekhonni.backend.repository.ProductRepository;
import com.ekhonni.backend.specificationbuilder.ProductSpecificationBuilder;
import com.ekhonni.backend.util.AuthUtil;
import com.ekhonni.backend.util.ImageUploadUtil;
import com.ekhonni.backend.util.ProductProjectionConverter;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;

import org.springframework.stereotype.Service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public void create(ProductCreateDTO productCreateDTO) {

        try {
            User user = AuthUtil.getAuthenticatedUser();
            Category category = categoryRepository.findByName(productCreateDTO.category());

            List<String> imagePaths = ImageUploadUtil.saveImage(UPLOAD_DIR, productCreateDTO.images());
            List<ProductImage> images = new ArrayList<>();
            for (String imagePath : imagePaths) {
                ProductImage image = new ProductImage(imagePath);
                images.add(image);
            }

            Product product = new Product(
                    productCreateDTO.name(),
                    productCreateDTO.price(),
                    productCreateDTO.description(),
                    false,
                    false,
                    productCreateDTO.condition(),
                    category,
                    user,
                    images
            );

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


    public Page<ProductResponseDTO> getAllFiltered(ProductFilter filter) {
        List<Long> categoryIds = categoryService.getActiveCategoryIds(filter.getCategoryName());
        Specification<Product> spec = ProductSpecificationBuilder.build(filter, categoryIds);
        Pageable pageable = PageRequest.of(filter.getPage(),filter.getSize());
        Page<ProductResponseDTO> productResponseDTOS = productRepository.findAllFiltered(spec,pageable);
        productResponseDTOS.forEach(productResponseDTO -> {
            productResponseDTO.setBids(null);
        });
        return productResponseDTOS;
    }


    public ProductResponseDTO getOne(Long id) {
        ProductProjection projection = productRepository.findProjectionById(id);

        ProductResponseDTO product = ProductProjectionConverter.convert(projection);
        product.setBids(null);
        return product;
    }
}
