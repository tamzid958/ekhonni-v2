/**
 * Author: Rifat Shariar Sakil
 * Time: 2:12 PM
 * Date: 12/3/2024
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.controller;


import com.ekhonni.backend.dto.ProductDTO;
import com.ekhonni.backend.enums.HTTPStatus;
import com.ekhonni.backend.filter.ProductFilter;
import com.ekhonni.backend.projection.ProductProjection;
import com.ekhonni.backend.response.ApiResponse;
import com.ekhonni.backend.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v2/product")
public record ProductController(ProductService productService) {


    @GetMapping
    public ApiResponse<?> getAllProducts(Pageable pageable) {
        Page<ProductProjection> productProjections = productService.getAll(ProductProjection.class, pageable);
        return new ApiResponse<>(HTTPStatus.FOUND, productProjections);
    }


    @PostMapping
    public ApiResponse<?> createProduct(@ModelAttribute ProductDTO productDTO) {
        productService.create(productDTO);
        return new ApiResponse<>(HTTPStatus.CREATED, null);
    }


    @GetMapping("/{id}")
    public ApiResponse<?> getOneProduct(@PathVariable Long id) {
        ProductProjection productProjection = productService.get(id, ProductProjection.class);
        return new ApiResponse<>(HTTPStatus.FOUND, productProjection);
    }


    @PatchMapping("/{id}")
    public ApiResponse<?> updateOneProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        ProductDTO updatedProductDTO = productService.update(id, productDTO);
        return new ApiResponse<>(HTTPStatus.FOUND, updatedProductDTO);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<?> deleteOneProduct(@PathVariable("id") Long id) {
        productService.softDelete(id);
        return new ApiResponse<>(HTTPStatus.DELETED, null);
    }


    @GetMapping("/filter")
    public ApiResponse<?> getSpec(@RequestBody ProductFilter filter) {
        return new ApiResponse<>(HTTPStatus.FOUND, productService.getAllFiltered(filter));
    }


}
