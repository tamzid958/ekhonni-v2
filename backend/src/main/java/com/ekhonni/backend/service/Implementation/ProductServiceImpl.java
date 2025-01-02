/**
 * Author: Rifat Shariar Sakil
 * Time: 6:20 PM
 * Date: 1/1/2025
 * Project Name: backend
 */

package com.ekhonni.backend.service.Implementation;

import com.ekhonni.backend.dto.ProductDTO;
import com.ekhonni.backend.enums.ProductSort;
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
import com.ekhonni.backend.util.AuthUtil;
import com.ekhonni.backend.util.ImageUploadUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl extends BaseService<Product, Long> implements ProductService {

    ProductRepository productRepository;
    CategoryService categoryService;
    CategoryRepository categoryRepository;

    @Value("${upload.dir}")
    String UPLOAD_DIR;


    public ProductServiceImpl(ProductRepository productRepository, CategoryService categoryService, CategoryRepository categoryRepository) {
        super(productRepository);
        this.productRepository = productRepository;
        this.categoryService = categoryService;
        this.categoryRepository = categoryRepository;
    }


    @Override
    @Transactional
    public void create(ProductDTO productDTO) {

        try {
            User user = AuthUtil.getAuthenticatedUser();
            Category category = categoryRepository.findByName(productDTO.category());
            String imagePath = ImageUploadUtil.saveImage(UPLOAD_DIR, productDTO.image());

            Product product = new Product(
                    productDTO.name(),
                    productDTO.price(),
                    productDTO.description(),
                    false,
                    false,
                    productDTO.condition(),
                    category,
                    user,
                    imagePath
            );
            productRepository.save(product);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }


    @Override
    public List<ProductProjection> getAllFiltered(ProductFilter productFilter) {
        if (productFilter.getSortBy() == null) productFilter.setSortBy(ProductSort.bestMatch);
        String categoryName = productFilter.getCategoryName();
        Category category = categoryRepository.findByNameAndActive(categoryName, true);
        return productRepository.findAllProjectionByFilter(productFilter, category.getId());
    }


    @Override
    public List<ProductProjection> search(String searchText, Pageable pageable) {
        return productRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrCategoryNameContainingIgnoreCase(searchText, searchText, searchText);
    }


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


}
