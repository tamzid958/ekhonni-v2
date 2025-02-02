/**
 * Author: Rifat Shariar Sakil
 * Time: 2:12 PM
 * Date: 12/3/2024
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.controller;


import com.ekhonni.backend.dto.product.ProductCreateDTO;
import com.ekhonni.backend.dto.product.ProductResponseDTO;
import com.ekhonni.backend.dto.product.ProductUpdateDTO;
import com.ekhonni.backend.enums.HTTPStatus;
import com.ekhonni.backend.filter.ProductFilter;
import com.ekhonni.backend.filter.UserProductFilter;
import com.ekhonni.backend.projection.bid.BuyerBidProjection;
import com.ekhonni.backend.response.ApiResponse;
import com.ekhonni.backend.service.BidService;
import com.ekhonni.backend.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v2/product")
@AllArgsConstructor
public class ProductController {
    ProductService productService;
    BidService bidService;



    @PostMapping
    @Operation(
            summary = "Create a new product",
            description = "Creates a new product using the provided details, including images",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Product creation form with images and details"
            )
    )
    public ApiResponse<?> create(
            @Parameter(description = "Product details for creation")
            @Valid @ModelAttribute ProductCreateDTO productCreateDTO) {

        productService.create(productCreateDTO);
        return new ApiResponse<>(HTTPStatus.CREATED, null);
    }


    @GetMapping("/{id}")
    public ApiResponse<?> getOne(@PathVariable Long id) {
        ProductResponseDTO product = productService.getOne(id);
        return new ApiResponse<>(HTTPStatus.FOUND, product);
    }



    @PatchMapping("/{id}")
    @PreAuthorize("@productService.getSellerId(#id) == authentication.principal.id")
    @Operation(
            summary = "Update a product",
            description = "Updates a product using the provided details",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Product update form with images and details"
            )
    )
    public ApiResponse<?> updateOne(@PathVariable Long id, @Valid @ModelAttribute ProductUpdateDTO dto) {
        return new ApiResponse<>(HTTPStatus.FOUND, productService.updateOne(id, dto));
    }



    @DeleteMapping("/{id}")
    @PreAuthorize("@productService.getSellerId(#id) == authentication.principal.id")
    public ApiResponse<?> deleteOne(@PathVariable("id") Long id) {
        productService.softDelete(id);
        return new ApiResponse<>(HTTPStatus.DELETED, null);
    }


    @GetMapping("/filter")
    public ApiResponse<?> getFiltered(@ModelAttribute ProductFilter filter) {
        return new ApiResponse<>(HTTPStatus.FOUND, productService.getAllFiltered(filter));
    }


    @GetMapping("/user/filter")
    public ApiResponse<?> getFilteredForUser(@ModelAttribute UserProductFilter filter) {
        return new ApiResponse<>(HTTPStatus.FOUND, productService.getAllFilteredForUser(filter));
    }


    @GetMapping("/{id}/bid")
    public ApiResponse<?> getAllBidsForProduct(@PathVariable("id") Long id, Pageable pageable) {
        return new ApiResponse<>(HTTPStatus.ACCEPTED,
                bidService.getAllForProduct(id, BuyerBidProjection.class, pageable));
    }


}
