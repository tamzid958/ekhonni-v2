/**
 * Author: Rifat Shariar Sakil
 * Time: 6:20 PM
 * Date: 1/1/2025
 * Project Name: backend
 */

package com.ekhonni.backend.service.Implementation;

import com.ekhonni.backend.dto.ProductDTO;
import com.ekhonni.backend.filter.ProductFilter;
import com.ekhonni.backend.model.Category;
import com.ekhonni.backend.model.Product;
import com.ekhonni.backend.model.User;
import com.ekhonni.backend.projection.ProductProjection;
import com.ekhonni.backend.repository.CategoryRepository;
import com.ekhonni.backend.repository.ProductRepository;
import com.ekhonni.backend.service.BaseService;
import com.ekhonni.backend.service.CategoryService;
import com.ekhonni.backend.service.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl extends BaseService<Product, Long> implements ProductService {

    ProductRepository productRepository;
    CategoryService categoryService;
    CategoryRepository categoryRepository;


    public ProductServiceImpl(ProductRepository productRepository, CategoryService categoryService, CategoryRepository categoryRepository) {
        super(productRepository);
        this.productRepository = productRepository;
        this.categoryService = categoryService;
        this.categoryRepository = categoryRepository;
    }


    @Override
    @Transactional
    public void create(ProductDTO productDTO) {
        // need to change something
        Authentication authenticated = SecurityContextHolder.getContext().getAuthentication();
        if (authenticated == null) {
            throw new RuntimeException("user not authenticated");
        }
        User user = (User) authenticated.getPrincipal();
        Category category = categoryRepository.findByName(productDTO.category());


        Product product = new Product(
                productDTO.name(),
                productDTO.price(),
                productDTO.description(),
                false,
                false,
                productDTO.condition(),
                category,
                user
        );
        productRepository.save(product);
    }


//    public ProductProjection updateOne(Long id, ProductDTO productDTO) {
//        this.update(id, productDTO);
//        return productRepository.findProductProjectionById(id);
//    }


    @Override
    public List<ProductProjection> getAllFiltered(ProductFilter productFilter) {
        String categoryName = productFilter.getCategoryName();
        Category category = categoryRepository.findByNameAndActive(categoryName, true);
        return productRepository.findAllProjectionByFilter(productFilter, category.getId());
    }


//    public Optional<ProductProjection> getOne(Long Id) {
//        return Optional.ofNullable(productRepository.findProductProjectionById(Id));
//    }

    @Override
    public List<ProductProjection> search(String searchText, Pageable pageable) {
        return productRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrCategoryNameContainingIgnoreCase(searchText, searchText, searchText);
    }


//    public boolean delete(Long id) {
//        if (productRepository.existsById(id)) {
//            productRepository.deleteById(id);
//            return true;
//        }
//        return false;
//    }

    @Override
    @Transactional
    public boolean approveProduct(Long id) {
        productRepository.findById(id).ifPresent(product -> {
            product.setApproved(true);
            productRepository.save(product);
        });
        return productRepository.existsById(id);
    }

    @Override
    public boolean declineProduct(Long id) {
        productRepository.findById(id).ifPresent(product -> {
            // notify seller
        });
        return true;
    }


//    public List<ProductProjection> getFilteredProducts(ProductFilterDTO filterDTO, Pageable pageable) {
//        Category category = categoryRepository.findByName(filterDTO.getCategoryName());
//        return productRepository.findAllFilteredProducts(category.getId());
//    }
}
