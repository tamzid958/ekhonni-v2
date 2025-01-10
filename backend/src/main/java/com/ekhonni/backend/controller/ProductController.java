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
import com.ekhonni.backend.response.ApiResponse;
import com.ekhonni.backend.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v2/product")
public record ProductController(ProductService productService) {

    @PostMapping
    public ApiResponse<?> createProduct(@Valid @ModelAttribute ProductCreateDTO productCreateDTO) {
        productService.create(productCreateDTO);
        return new ApiResponse<>(HTTPStatus.CREATED, null);
    }


    @GetMapping("/{id}")
    public ApiResponse<?> getOneProduct(@PathVariable Long id) {
        ProductResponseDTO product = productService.getOne(id);
        return new ApiResponse<>(HTTPStatus.FOUND, product);
    }


    @PatchMapping("/{id}")
    public ApiResponse<?> updateOneProduct(@PathVariable Long id, @Valid @ModelAttribute ProductUpdateDTO dto) {
        return new ApiResponse<>(HTTPStatus.FOUND, productService.updateOne(id, dto));
    }


    @DeleteMapping("/{id}")
    public ApiResponse<?> deleteOneProduct(@PathVariable("id") Long id) {
        productService.softDelete(id);
        return new ApiResponse<>(HTTPStatus.DELETED, null);
    }


    @GetMapping("/filter")
    public ApiResponse<?> getFilteredProducts(@RequestBody ProductFilter filter) {
        return new ApiResponse<>(HTTPStatus.FOUND, productService.getAllFiltered(filter));
    }


}
