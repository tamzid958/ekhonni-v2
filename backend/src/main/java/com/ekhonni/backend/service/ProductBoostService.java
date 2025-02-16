/**
 * Author: Rifat Shariar Sakil
 * Time: 3:37 PM
 * Date: 2/12/25
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.service;


import com.ekhonni.backend.dto.product.ProductBoostDTO;
import com.ekhonni.backend.enums.ProductStatus;
import com.ekhonni.backend.exception.ProductNotFoundException;
import com.ekhonni.backend.model.Account;
import com.ekhonni.backend.model.Product;
import com.ekhonni.backend.model.ProductBoost;
import com.ekhonni.backend.model.User;
import com.ekhonni.backend.repository.ProductBoostRepository;
import com.ekhonni.backend.repository.ProductRepository;
import com.ekhonni.backend.util.AuthUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class ProductBoostService {
    private final double minimumBalance = 50;
    ProductRepository productRepository;
    ProductBoostRepository productBoostRepository;
    AccountService accountService;

    public ProductBoostService(ProductRepository productRepository, ProductBoostRepository productBoostRepository,
                               AccountService accountService
    ) {
        this.productRepository = productRepository;
        this.productBoostRepository = productBoostRepository;
        this.accountService = accountService;
    }


    @Transactional
    public void boostProduct(ProductBoostDTO boostDTO) {
        User seller = AuthUtil.getAuthenticatedUser();
        Product product = productRepository.findById(boostDTO.getProductId())
                .orElseThrow(() -> new ProductNotFoundException("Product not found for boost"));

        validateProductForBoost(product);

        LocalDateTime now = LocalDateTime.now();
        boolean hasActiveBoost = findActiveBoost(product.getId(), now);
        if (hasActiveBoost) {
            throw new ProductNotFoundException("An active boost already exists for this product.");
        }


        Account sellerAccount = accountService.getByUserId(seller.getId());
        accountService.deduct(sellerAccount, boostDTO.getBoostType().getAmount());


        ProductBoost boost = createProductBoost(product, boostDTO, now);
        productBoostRepository.save(boost);
    }

    private void validateProductForBoost(Product product) {
        if (product.getStatus() != ProductStatus.APPROVED) {
            throw new ProductNotFoundException("Product is not eligible for boost");
        }
    }

    private boolean findActiveBoost(Long productId, LocalDateTime now) {
        return productBoostRepository.findActiveBoostByProductId(productId, now).isPresent();
    }


    private ProductBoost createProductBoost(Product product, ProductBoostDTO boostDTO, LocalDateTime now) {
        ProductBoost boost = new ProductBoost();
        boost.setBoostType(boostDTO.getBoostType());
        boost.setProduct(product);
        return boost;
    }


    @Transactional
    public void updateBoost(ProductBoostDTO boostDTO) {
        User seller = AuthUtil.getAuthenticatedUser();
        Product product = productRepository.findById(boostDTO.getProductId())
                .orElseThrow(() -> new ProductNotFoundException("Product not found for boost"));

        validateProductForBoost(product);

        LocalDateTime now = LocalDateTime.now();
        ProductBoost boost = productBoostRepository.findByProductId(product.getId())
                .orElseThrow(() -> new ProductNotFoundException("No boost exists for this product to update"));

        Account sellerAccount = accountService.getByUserId(seller.getId());
        accountService.deduct(sellerAccount, boostDTO.getBoostType().getAmount());

        boost.setExpiresAt(now.plus(boostDTO.getBoostType().getDuration(), boostDTO.getBoostType().getUnit()));

    }

    @Transactional
    @Modifying
    public void removeBoost(Long productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found for boost"));

        validateProductForBoost(product);

        ProductBoost boost = productBoostRepository.findByProductId(product.getId())
                .orElseThrow(() -> new ProductNotFoundException("No boost exists for this product to delete"));


        productBoostRepository.delete(boost);
    }


    /*
      Scheduler to delete expired boosted product
    */
    @Scheduled(cron = "0 1 0,12 * * ?")  // Runs at 12:01 AM and 12:01 PM
    @Transactional
    public void removeExpiredBoosts() {
        List<ProductBoost> expiredBoosts = productBoostRepository.findAllByExpiresAtBefore(LocalDateTime.now());
        if (!expiredBoosts.isEmpty()) {
            productBoostRepository.deleteAll(expiredBoosts);
            log.info("Deleted {} expired boost products", expiredBoosts.size());
        } else {
            log.info("Nothing to delete");
        }
    }
}
