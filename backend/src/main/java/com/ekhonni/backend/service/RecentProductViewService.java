/**
 * Author: Rifat Shariar Sakil
 * Time: 2:35 PM
 * Date: 2/24/25
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.service;

import com.ekhonni.backend.dto.product.ProductBoostResponseDTO;
import com.ekhonni.backend.dto.product.ProductResponseDTO;
import com.ekhonni.backend.enums.ProductStatus;
import com.ekhonni.backend.exception.ProductNotFoundException;
import com.ekhonni.backend.model.Product;
import com.ekhonni.backend.model.RecentlyViewedProduct;
import com.ekhonni.backend.model.User;
import com.ekhonni.backend.model.WatchlistProduct;
import com.ekhonni.backend.projection.ProductProjection;
import com.ekhonni.backend.projection.RecentViewedProductProjection;
import com.ekhonni.backend.projection.WatchlistProductProjection;
import com.ekhonni.backend.repository.ProductBoostRepository;
import com.ekhonni.backend.repository.ProductRepository;
import com.ekhonni.backend.repository.RecentProductViewRepository;
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
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RecentProductViewService {
    private final ProductRepository productRepository;
    private final RecentProductViewRepository recentProductViewRepository;
    private final ProductBoostRepository productBoostRepository;


    public void addProduct(User user, Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found to add to recent-view"));

        boolean inRecentViewAlready = recentProductViewRepository.existsByUser_IdAndProduct_Id(user.getId(),id);
        if(inRecentViewAlready) return;

        RecentlyViewedProduct recentlyViewedProduct = new RecentlyViewedProduct();
        recentlyViewedProduct.setUser(user);
        recentlyViewedProduct.setProduct(product);

        recentProductViewRepository.save(recentlyViewedProduct);


    }

    public ProductResponseDTO convertToProductResponseDTO(ProductProjection projection) {
        ProductResponseDTO dto = ProductProjectionConverter.convert(projection);
        productBoostRepository.findByProductId(projection.getId()).ifPresent(boost ->
                dto.setBoostData(new ProductBoostResponseDTO(
                        boost.getBoostType(),
                        boost.getBoostedAt(),
                        boost.getExpiresAt()))
        );
        return dto;
    }

    public Page<ProductResponseDTO> getAllProducts(Pageable pageable) {
        User user = AuthUtil.getAuthenticatedUser();
        Page<RecentViewedProductProjection> recentViewedProducts = recentProductViewRepository.findAllByUserOrderByCreatedAtDesc(user, pageable);

        List<ProductResponseDTO> products = recentViewedProducts.getContent().stream()
                .map(recentView -> convertToProductResponseDTO(recentView.getProduct()))
                .collect(Collectors.toList());

        return new PageImpl<>(products, pageable, recentViewedProducts.getTotalElements());
    }

    @Transactional
    public String removeProducts(List<Long> productIds) {

        User user = AuthUtil.getAuthenticatedUser();
        Long foundCount = productRepository.countByIdInAndStatus(productIds, ProductStatus.APPROVED);

        if (foundCount != productIds.size()) {
            throw new ProductNotFoundException("Some products not found");
        }
        // need to check the productIds by that user in recent-view

        recentProductViewRepository.deleteByUserIdAndProductIdIn(user.getId(), productIds);
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
        recentProductViewRepository.deleteByProductIdIn(productIds);

    }



    @Transactional
    public String clearAll() {
        User user = AuthUtil.getAuthenticatedUser();
        recentProductViewRepository.deleteAllByUserId(user.getId());
        return "Removed selected products from the watchlist";
    }
}
