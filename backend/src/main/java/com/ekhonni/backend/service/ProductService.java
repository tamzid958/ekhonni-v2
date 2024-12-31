/**
 * Author: Rifat Shariar Sakil
 * Time: 2:25 PM
 * Date: 12/3/2024
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.service;


import com.ekhonni.backend.dto.ProductDTO;
import com.ekhonni.backend.model.Category;
import com.ekhonni.backend.model.Product;
import com.ekhonni.backend.model.User;
import com.ekhonni.backend.projection.ProductProjection;
import com.ekhonni.backend.repository.CategoryRepository;
import com.ekhonni.backend.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService extends BaseService<Product, Long> {

    ProductRepository productRepository;
    CategoryService categoryService;
    CategoryRepository categoryRepository;


    public ProductService(ProductRepository productRepository, CategoryService categoryService, CategoryRepository categoryRepository) {
        super(productRepository);
        this.productRepository = productRepository;
        this.categoryService = categoryService;
        this.categoryRepository = categoryRepository;
    }


    @Transactional
    public void create(ProductDTO productDTO) {
        // need to change something
        Authentication authenticated = SecurityContextHolder.getContext().getAuthentication();
        if (authenticated == null) {
            throw new RuntimeException("user not authenticated");
        }
        User user = (User) authenticated.getPrincipal();
        Category category = categoryService.getByName(productDTO.category());

        List<Category> categories = new ArrayList<>();
        categories.add(category);

        if (category.getName().equals("Asian")) {
            Category category1 = categoryService.getByName("People");
            categories.add(category1);
        }


        Product product = new Product(
                productDTO.name(),
                productDTO.price(),
                productDTO.description(),
                false,
                false,
                productDTO.condition(),
                category,
                categories,
                user
        );
        productRepository.save(product);
    }


//    public ProductProjection updateOne(Long id, ProductDTO productDTO) {
//        this.update(id, productDTO);
//        return productRepository.findProductProjectionById(id);
//    }


//    public Page<ProductProjection> getAllByCategoryName(String categoryName, Pageable pageable) {
//        Category category = categoryRepository.findByNameAndActive(categoryName, true);
//        return productRepository.findAllProjectionByCategoryId(category.getId(), pageable);
//    }

    public Page<ProductProjection> getAllByCategoryName(String categoryName, Pageable pageable) {
        Category category = categoryRepository.findByNameAndActive(categoryName, true);
        return productRepository.findAllByCategoriesName(category.getName(), pageable);
    }


//    public Optional<ProductProjection> getOne(Long Id) {
//        return Optional.ofNullable(productRepository.findProductProjectionById(Id));
//    }

    public Page<ProductProjection> search(String searchText, Pageable pageable) {
        return productRepository.searchProducts(searchText, pageable);
    }


//    public boolean delete(Long id) {
//        if (productRepository.existsById(id)) {
//            productRepository.deleteById(id);
//            return true;
//        }
//        return false;
//    }

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


}
