/**
 * Author: Rifat Shariar Sakil
 * Time: 2:12 PM
 * Date: 12/3/2024
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.controller;


import com.ekhonni.backend.dto.ProductCreateDTO;
import com.ekhonni.backend.dto.ProductResponseDTO;
import com.ekhonni.backend.dto.ProductUpdateDTO;
import com.ekhonni.backend.enums.HTTPStatus;
import com.ekhonni.backend.filter.ProductFilter;
import com.ekhonni.backend.projection.BuyerBidProjection;
import com.ekhonni.backend.response.ApiResponse;
import com.ekhonni.backend.service.BidService;
import com.ekhonni.backend.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v2/product")
public record ProductController(ProductService productService, BidService bidService) {

    @PostMapping
    public ApiResponse<?> create(@Valid @ModelAttribute ProductCreateDTO productCreateDTO) {
        productService.create(productCreateDTO);
        return new ApiResponse<>(HTTPStatus.CREATED, null);
    }


    @GetMapping("/{id}")
    public ApiResponse<?> getOne(@PathVariable Long id) {
        ProductResponseDTO product = productService.getOne(id);
        return new ApiResponse<>(HTTPStatus.FOUND, product);
    }


    @PatchMapping("/{id}")
    public ApiResponse<?> updateOne(@PathVariable Long id, @Valid @ModelAttribute ProductUpdateDTO dto) {
        return new ApiResponse<>(HTTPStatus.FOUND, productService.updateOne(id, dto));
    }


    @DeleteMapping("/{id}")
    public ApiResponse<?> deleteOne(@PathVariable("id") Long id) {
        productService.softDelete(id);
        return new ApiResponse<>(HTTPStatus.DELETED, null);
    }


    @GetMapping("/filter")
    public ApiResponse<?> getFiltered(@ModelAttribute ProductFilter filter) {
        System.out.println(filter.getCategoryName());
        return new ApiResponse<>(HTTPStatus.FOUND, productService.getAllFiltered(filter));
    }



    @GetMapping("/{id}/bid")
    public ApiResponse<?> getAllBidsForProduct(@PathVariable("id") Long id, Pageable pageable) {
        return new ApiResponse<>(HTTPStatus.ACCEPTED,
                bidService.getAllBidsForProduct(id, BuyerBidProjection.class, pageable));
    }



}
