/**
 * Author: Rifat Shariar Sakil
 * Time: 2:25 PM
 * Date: 12/3/2024
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.service;


import com.ekhonni.backend.dto.ProductDTO;
import com.ekhonni.backend.enums.ProductSort;
import com.ekhonni.backend.filter.ProductFilter;
import com.ekhonni.backend.model.Category;
import com.ekhonni.backend.model.Product;
import com.ekhonni.backend.model.User;
import com.ekhonni.backend.projection.ProductProjection;
import com.ekhonni.backend.repository.CategoryRepository;
import com.ekhonni.backend.repository.ProductRepository;
import com.ekhonni.backend.specification.ProductSpecification;
import com.ekhonni.backend.util.AuthUtil;
import com.ekhonni.backend.util.ImageUploadUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

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

            productRepository.save(product);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }


    public List<ProductProjection> getAllFiltered(ProductFilter productFilter) {
        if (productFilter.getSortBy() == null) productFilter.setSortBy(ProductSort.bestMatch);
        String categoryName = productFilter.getCategoryName();
        Category category = categoryRepository.findByNameAndActive(categoryName, true);
        return productRepository.findAllProjectionByFilter(productFilter, category.getId());


    }


    public List<ProductProjection> search(String searchText, Pageable pageable) {
        return productRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrCategoryNameContainingIgnoreCase(searchText, searchText, searchText);
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

    public List<String> getImages(Long id) {
        return productRepository.findImagePathsById(id);
    }

    public List<Long> getCategoryIds(String name) {
        Category category = categoryRepository.findByName(name);
        Long categoryId = category.getId();
        return categoryRepository.findSubCategoryIds(categoryId);


    }

    public List<Product> getAllProductProjection(String name) {
        List<Long> categoryIds = getCategoryIds(name);
        System.out.println(categoryIds);
        return productRepository.findByCategoryIdIn(categoryIds);

    }

    public List<Product> checkSpecification(String name, Double minPrice, Double maxPrice) {
        List<Long> categoryIds = getCategoryIds(name);

        Specification<Product> spec = Specification.where(null);

        if (minPrice != null) {
            spec = spec.and(ProductSpecification.hasMinimumPrice(minPrice));
        }

        if (maxPrice != null) {
            spec = spec.and(ProductSpecification.hasMaximumPrice(maxPrice));
        }

        if (categoryIds != null && !categoryIds.isEmpty()) {
            spec = spec.and(ProductSpecification.belongsToCategories(categoryIds));
        }

        return productRepository.findAll(spec);
    }
}
