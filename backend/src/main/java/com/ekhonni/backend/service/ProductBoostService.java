/**
 * Author: Rifat Shariar Sakil
 * Time: 3:37 PM
 * Date: 2/12/25
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.service;


import com.ekhonni.backend.dto.product.ProductBoostDTO;
import com.ekhonni.backend.dto.product.ProductCreateDTO;
import com.ekhonni.backend.dto.product.ProductResponseDTO;
import com.ekhonni.backend.dto.product.ProductUpdateDTO;
import com.ekhonni.backend.enums.ProductStatus;
import com.ekhonni.backend.exception.CategoryNotFoundException;
import com.ekhonni.backend.exception.ProductNotCreatedException;
import com.ekhonni.backend.exception.ProductNotFoundException;
import com.ekhonni.backend.exception.ProductNotUpdatedException;
import com.ekhonni.backend.filter.ProductFilter;
import com.ekhonni.backend.filter.SellerProductFilter;
import com.ekhonni.backend.filter.UserProductFilter;
import com.ekhonni.backend.model.*;
import com.ekhonni.backend.projection.ProductProjection;
import com.ekhonni.backend.repository.CategoryRepository;
import com.ekhonni.backend.repository.ProductBoostRepository;
import com.ekhonni.backend.repository.ProductRepository;
import com.ekhonni.backend.repository.UserRepository;
import com.ekhonni.backend.specification.SpecificationResult;
import com.ekhonni.backend.specificationbuilder.CommonProductSpecificationBuilder;
import com.ekhonni.backend.specificationbuilder.SellerProductSpecificationBuilder;
import com.ekhonni.backend.specificationbuilder.UserProductSpecificationBuilder;
import com.ekhonni.backend.util.AuthUtil;
import com.ekhonni.backend.util.CloudinaryImageUploadUtil;
import com.ekhonni.backend.util.PaginationUtil;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductBoostService {
    ProductRepository productRepository;
    ProductBoostRepository productBoostRepository;
    AccountService accountService;


    private final double minimumBalance = 50;

    public ProductBoostService(ProductRepository productRepository,ProductBoostRepository productBoostRepository,
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
        ensureNoActiveBoost(product.getId(), now);

        Account account = accountService.getByUserId(seller.getId());
        deductBoostAmount(account, boostDTO.getBoostType().getAmount());

        ProductBoost boost = createProductBoost(product, boostDTO, now);
        productBoostRepository.save(boost);
    }

    private void validateProductForBoost(Product product) {
        if (product.getStatus() != ProductStatus.APPROVED) {
            throw new ProductNotFoundException("Product is not eligible for boost");
        }
    }

    private void ensureNoActiveBoost(Long productId, LocalDateTime now) {
        boolean hasActiveBoost = productBoostRepository.findActiveBoostByProductId(productId, now).isPresent();
        if (hasActiveBoost) {
            throw new ProductNotFoundException("An active boost already exists for this product.");
        }
    }

    private void deductBoostAmount(Account account, double boostAmount) {
        if (account.getBalance()+minimumBalance< boostAmount) {
            throw new ProductNotFoundException("Not enough money in your account.");
        }
        updateAccount(account, boostAmount);
    }

    @Transactional
    public void updateAccount(Account userAccount, double boostAmount){
        userAccount.setTotalWithdrawals(userAccount.getTotalWithdrawals()+boostAmount);
        Account superAdminAccount = accountService.getSuperAdminAccount();
        superAdminAccount.setTotalWithdrawals(superAdminAccount.getTotalWithdrawals()+boostAmount);
    }

    private ProductBoost createProductBoost(Product product, ProductBoostDTO boostDTO, LocalDateTime now) {
        ProductBoost boost = new ProductBoost();
        boost.setBoostType(boostDTO.getBoostType());
        boost.setProduct(product);
        boost.setBoostedAt(now);
        boost.setExpiresAt(now.plus(boostDTO.getBoostType().getDuration(), boostDTO.getBoostType().getUnit()));
        return boost;
    }
}
