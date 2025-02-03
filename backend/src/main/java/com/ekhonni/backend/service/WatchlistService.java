/**
 * Author: Rifat Shariar Sakil
 * Time: 7:21 PM
 * Date: 2/3/25
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.service;

import com.ekhonni.backend.dto.product.ProductResponseDTO;
import com.ekhonni.backend.exception.ProductNotFoundException;
import com.ekhonni.backend.exception.UserNotFoundException;
import com.ekhonni.backend.model.Product;
import com.ekhonni.backend.model.User;
import com.ekhonni.backend.model.WatchlistProduct;
import com.ekhonni.backend.projection.ProductProjection;
import com.ekhonni.backend.projection.WatchlistProductProjection;
import com.ekhonni.backend.repository.ProductRepository;
import com.ekhonni.backend.repository.WatchlistRepository;
import com.ekhonni.backend.util.AuthUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class WatchlistService {

    private final ProductRepository productRepository;
    private final WatchlistRepository watchlistRepository;


    public void addProduct(Long id){
        User user = AuthUtil.getAuthenticatedUser();

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found for update"));

        WatchlistProduct watchlistProduct = new WatchlistProduct();
        watchlistProduct.setUser(user);
        watchlistProduct.setProduct(product);

        watchlistRepository.save(watchlistProduct);


    }

    public Page<?> getAllProducts(Pageable pageable) {
        User user = AuthUtil.getAuthenticatedUser();

        Page<WatchlistProductProjection> watchlistProducts = watchlistRepository.findAllProductIdsByUser(user, pageable );
        System.out.println(watchlistProducts);
        return watchlistProducts;



    }
}
