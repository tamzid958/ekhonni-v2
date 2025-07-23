/**
 * Author: Rifat Shariar Sakil
 * Time: 7:21 PM
 * Date: 2/3/25
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.service;

import com.ekhonni.backend.dto.product.ProductResponseDTO;
import com.ekhonni.backend.enums.ProductStatus;
import com.ekhonni.backend.exception.ProductNotFoundException;
import com.ekhonni.backend.model.Product;
import com.ekhonni.backend.model.User;
import com.ekhonni.backend.model.WatchlistProduct;
import com.ekhonni.backend.projection.WatchlistProductProjection;
import com.ekhonni.backend.repository.ProductRepository;
import com.ekhonni.backend.repository.WatchlistRepository;
import com.ekhonni.backend.util.AuthUtil;
import com.ekhonni.backend.util.ProductProjectionConverter;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class WatchlistService {

    private final ProductRepository productRepository;
    private final WatchlistRepository watchlistRepository;


    public void addProduct(Long id) {
        User user = AuthUtil.getAuthenticatedUser();

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found to add to watchlist"));

        if (product.getStatus() != ProductStatus.APPROVED)
            throw new ProductNotFoundException("Product must be approved to add to watchlist");

        WatchlistProduct watchlistProduct = new WatchlistProduct();
        watchlistProduct.setUser(user);
        watchlistProduct.setProduct(product);

        watchlistRepository.save(watchlistProduct);


    }

    public Page<ProductResponseDTO> getAllProducts(Pageable pageable) {
        User user = AuthUtil.getAuthenticatedUser();
        Page<WatchlistProductProjection> watchlistProducts = watchlistRepository.findAllProjectionByUser(user, pageable);
        List<ProductResponseDTO> products = watchlistProducts.getContent().stream()
                .map(watchlistProduct -> ProductProjectionConverter.convert(watchlistProduct.getProduct()))
                .toList();
        return new PageImpl<>(products, pageable, watchlistProducts.getTotalElements());
    }

    @Transactional
    public String removeProducts(List<Long> productIds) {

        User user = AuthUtil.getAuthenticatedUser();
        Long foundCount = productRepository.countByIdInAndStatus(productIds, ProductStatus.APPROVED);

        if (foundCount != productIds.size()) {
            throw new ProductNotFoundException("Some products not found");
        }
        // need to check the productIds by that user in watchlist
        
        watchlistRepository.deleteByUserIdAndProductIdIn(user.getId(), productIds);
        return "Removed selected products from the watchlist";
    }



    @Transactional
    public void removeProductFromAllUser(Long productId) {
        List<Long>productIds = new ArrayList<>();
        productIds.add(productId);
        Long foundCount = productRepository.countByIdInAndStatus(productIds, ProductStatus.SOLD);
        if (foundCount != productIds.size()) {
            throw new ProductNotFoundException("Product Not Found");
        }
        watchlistRepository.deleteByProductIdIn(productIds);

    }

    public Boolean productInWatchlist(Long productId) {
           User user = AuthUtil.getAuthenticatedUser();
           return watchlistRepository.existsByUser_IdAndProduct_Id(user.getId(),productId);
    }
}
