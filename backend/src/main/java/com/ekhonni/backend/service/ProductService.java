/**
 * Author: Rifat Shariar Sakil
 * Time: 2:25 PM
 * Date: 12/3/2024
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.service;


import com.ekhonni.backend.dto.product.ProductCreateDTO;
import com.ekhonni.backend.dto.product.ProductResponseDTO;
import com.ekhonni.backend.dto.product.ProductUpdateDTO;
import com.ekhonni.backend.enums.ProductStatus;
import com.ekhonni.backend.exception.CategoryNotFoundException;
import com.ekhonni.backend.exception.ProductNotCreatedException;
import com.ekhonni.backend.exception.ProductNotFoundException;
import com.ekhonni.backend.exception.ProductNotUpdatedException;
import com.ekhonni.backend.filter.ProductFilter;
import com.ekhonni.backend.filter.UserProductFilter;
import com.ekhonni.backend.model.Category;
import com.ekhonni.backend.model.Product;
import com.ekhonni.backend.model.ProductImage;
import com.ekhonni.backend.model.User;
import com.ekhonni.backend.projection.ProductProjection;
import com.ekhonni.backend.repository.CategoryRepository;
import com.ekhonni.backend.repository.ProductRepository;
import com.ekhonni.backend.repository.UserRepository;
import com.ekhonni.backend.specification.SpecificationResult;
import com.ekhonni.backend.specificationbuilder.CommonProductSpecificationBuilder;
import com.ekhonni.backend.specificationbuilder.UserProductSpecificationBuilder;
import com.ekhonni.backend.util.AuthUtil;
import com.ekhonni.backend.util.CloudinaryImageUploadUtil;
import com.ekhonni.backend.util.ImageUtil;
import com.ekhonni.backend.util.ProductProjectionConverter;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ProductService extends BaseService<Product, Long> {
    ProductRepository productRepository;
    CategoryService categoryService;
    CategoryRepository categoryRepository;
    UserRepository userRepository;
    CloudinaryImageUploadUtil cloudinaryImageUploadUtil;

    @Value("${product.upload.dir}")
    String PRODUCT_UPLOAD_DIR;


    public ProductService(ProductRepository productRepository, CategoryService categoryService, CategoryRepository categoryRepository, UserRepository userRepository, CloudinaryImageUploadUtil cloudinaryImageUploadUtil) {
        super(productRepository);
        this.productRepository = productRepository;
        this.categoryService = categoryService;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.cloudinaryImageUploadUtil = cloudinaryImageUploadUtil;
    }


    @Transactional
    public void create(ProductCreateDTO dto) {

        try {
            User seller = AuthUtil.getAuthenticatedUser();
            Category category = categoryRepository.findByNameAndActive(dto.category(), true);
            if (category == null) throw new CategoryNotFoundException("category by this name not found");

            List<String> imagePaths = cloudinaryImageUploadUtil.uploadImages(dto.images());
            List<ProductImage> images = new ArrayList<>();
            for (String imagePath : imagePaths) {
                ProductImage image = new ProductImage(imagePath);
                images.add(image);
            }

            ProductStatus status = ProductStatus.PENDING_APPROVAL;

            Product product = new Product(
                    dto.title(),
                    dto.subTitle(),
                    dto.description(),
                    dto.price(),
                    dto.division(),
                    dto.address(),
                    status,
                    dto.condition(),
                    dto.conditionDetails(),
                    category,
                    seller,
                    images
            );

            productRepository.save(product);
        } catch (Exception e) {
            throw new ProductNotCreatedException(e.getMessage());
        }

    }


    public Page<ProductResponseDTO> getAllFiltered(ProductFilter filter) {
        List<Long> categoryIds = new ArrayList<>();
        if (filter.getCategoryName() != null && !filter.getCategoryName().isEmpty()) {
            categoryIds = categoryService.getRelatedActiveIds(filter.getCategoryName());
        }


        SpecificationResult specificationResult = CommonProductSpecificationBuilder.build(filter, categoryIds);
        Specification<Product> spec = specificationResult.getSpec();
        Pageable pageable = PageRequest.of(filter.getPage(), filter.getSize());
        List<Long> productIds = productRepository.findAllFiltered(spec, pageable);
        List<ProductProjection> projections = productRepository.findByIdIn(productIds);
        List<ProductResponseDTO> products = projections.stream()
                .map(ProductProjectionConverter::convert)
                .toList();
        long totalElements = 0;
        return new PageImpl<>(products, pageable, totalElements);
    }


    public ProductResponseDTO getOne(Long id) {
        ProductProjection projection = productRepository.findProjectionById(id);
        return ProductProjectionConverter.convert(projection);
    }


    @Modifying
    @Transactional
    public String updateOne(Long id, ProductUpdateDTO dto) {

        try {
            Product product = productRepository.findById(id)
                    .orElseThrow(() -> new ProductNotFoundException("Product not found for update"));

            Category category = categoryRepository.findByName(dto.category());
            if (category == null) throw new CategoryNotFoundException("category by this name not found");


            List<String> imagePaths = ImageUtil.saveImage(PRODUCT_UPLOAD_DIR, dto.images());
            List<ProductImage> newImages = new ArrayList<>();
            for (String imagePath : imagePaths) {
                newImages.add(new ProductImage(imagePath));
            }

            product.getImages().clear();
            product.getImages().addAll(newImages);
            product.setTitle(dto.title());
            product.setSubTitle(dto.subTitle());
            product.setDescription(dto.description());
            product.setPrice(dto.price());
            product.setDivision(dto.division());
            product.setAddress(dto.address());
            product.setCondition(dto.condition());
            product.setConditionDetails(dto.conditionDetails());
            product.setCategory(category);


            productRepository.save(product);
            return "updated";
        } catch (Exception e) {
            throw new ProductNotUpdatedException(e.getMessage());
        }

    }


    public UUID getSellerId(Long id) {
        return productRepository.findSellerIdById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
    }

    public Page<ProductResponseDTO> getAllFilteredForUser(UserProductFilter filter) {
        List<Long> categoryIds = new ArrayList<>();
        if (filter.getCategoryName() != null && !filter.getCategoryName().isEmpty()) {
            categoryIds = categoryService.getRelatedActiveIds(filter.getCategoryName());
        }


        Specification<Product> spec = UserProductSpecificationBuilder.build(filter, categoryIds);
        Pageable pageable = PageRequest.of(filter.getPage(), filter.getSize());
        List<Long> productIds = productRepository.findAllFiltered(spec, pageable);
        List<ProductProjection> projections = productRepository.findByIdIn(productIds);
        List<ProductResponseDTO> products = projections.stream()
                .map(ProductProjectionConverter::convert)
                .toList();
        long totalElements = 0;
        return new PageImpl<>(products, pageable, totalElements);
    }
}
