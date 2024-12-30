/**
 * Author: Rifat Shariar Sakil
 * Time: 2:25 PM
 * Date: 12/3/2024
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.service;


import com.ekhonni.backend.model.Product;
import com.ekhonni.backend.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductService extends BaseService<Product, Long> {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        super(productRepository);
        this.productRepository = productRepository;
    }
    public Product create(Product product) {
        return productRepository.save(product);
    }
}
