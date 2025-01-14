package com.ekhonni.backend.controller;


import com.ekhonni.backend.dto.bid.BidCreateDTO;
import com.ekhonni.backend.dto.bid.BidUpdateDTO;
import com.ekhonni.backend.enums.HTTPStatus;
import com.ekhonni.backend.projection.BuyerBidProjection;
import com.ekhonni.backend.projection.SellerBidProjection;
import com.ekhonni.backend.response.ApiResponse;
import com.ekhonni.backend.service.BidService;
import com.ekhonni.backend.service.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v2/bid")
@AllArgsConstructor
@Tag(name = "Bid", description = "Manage bid operations")
public class BidController {

    BidService bidService;
    ProductService productService;

    @GetMapping("/{id}")
    public ApiResponse<?> get(@PathVariable Long id) {
        return new ApiResponse<>(HTTPStatus.ACCEPTED, bidService.get(id, SellerBidProjection.class)) ;
    }

    @GetMapping("/seller/{product_id}")
    @PreAuthorize("@productService.getSellerId(#productId)== authentication.principal.id")
    public ApiResponse<?> getAllForProductSeller(@PathVariable("product_id") Long productId, Pageable pageable) {
        return new ApiResponse<>(HTTPStatus.ACCEPTED,
                bidService.getAllBidsForProduct(productId, SellerBidProjection.class, pageable));
    }

    @GetMapping("/buyer/{product_id}")
    public ApiResponse<?> getAllForProductBuyer(@PathVariable("product_id") Long productId, Pageable pageable) {
        return new ApiResponse<>(HTTPStatus.ACCEPTED,
                bidService.getAllBidsForProduct(productId, BuyerBidProjection.class, pageable));
    }

    @PostMapping()
    public ApiResponse<?> create(@Valid @RequestBody BidCreateDTO bidCreateDTO) {
        return new ApiResponse<>(HTTPStatus.CREATED, bidService.create(bidCreateDTO));
    }

    @PatchMapping("/{id}/update")
    @PreAuthorize("@bidService.getBidderId(#id) == authentication.principal.id")
    public ApiResponse<?> update(@PathVariable Long id, @Valid @RequestBody BidCreateDTO bidCreateDTO) {
        return new ApiResponse<>(HTTPStatus.ACCEPTED, bidService.updateBid(id, bidCreateDTO));
    }

    @PatchMapping("/{id}/accept")
    @PreAuthorize("@bidService.isProductOwner(authentication.principal.id, #id)")
    public ApiResponse<?> accept(@PathVariable Long id) {
        bidService.accept(id);
        return new ApiResponse<>(HTTPStatus.ACCEPTED, null);
    }

    // Admin api

    @GetMapping
    public ApiResponse<?> getAll(Pageable pageable) {
        return new ApiResponse<>(HTTPStatus.ACCEPTED, bidService.getAll(SellerBidProjection.class, pageable));
    }
    @DeleteMapping("/{id}")
    public ApiResponse<?> delete(@PathVariable Long id) {
        bidService.softDelete(id);
        return new ApiResponse<>(HTTPStatus.DELETED, null);
    }

    @DeleteMapping("/{id}/delete-permanently")
    public ApiResponse<?> deletePermanently(@PathVariable Long id) {
        bidService.deletePermanently(id);
        return new ApiResponse<>(HTTPStatus.DELETED, null);
    }

}
