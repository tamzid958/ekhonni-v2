/**
 * Author: Rifat Shariar Sakil
 * Time: 2:12 PM
 * Date: 12/3/2024
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.controller;



import com.ekhonni.backend.dto.ProductPageDTO;
import com.ekhonni.backend.enums.HTTPStatus;
import com.ekhonni.backend.model.Product;
import com.ekhonni.backend.projection.ProductProjection;
import com.ekhonni.backend.response.ApiResponse;
import com.ekhonni.backend.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/products")
public record ProductController(ProductService productService){


    @GetMapping
    public ApiResponse<?> getPage(@RequestParam(name = "pageNo", required = true) Integer pageNo){
        Pageable pageable = new ProductPageDTO().getPageable(pageNo);
        Page<ProductProjection> productProjections =  productService.getAll(pageable);
        return ApiResponse.setResponse(HTTPStatus.FOUND, true, productProjections, "products fetched successfully");
    }


    @GetMapping("/by-categories/{category_id}")
    public ApiResponse<?> getByCategoryId(@PathVariable("category_id") Long categoryId){
        List<ProductProjection>productProjections = productService.getAllByCategoryId(categoryId);
        return ApiResponse.setResponse(HTTPStatus.FOUND, true, productProjections, "all products given under one category");
    }

//    @GetMapping("/by-categories/{category_id}")
//    public ApiResponse<?> getByCategoryId(@RequestParam(name = "pageNo",required = true) Integer pageNo, @PathVariable("category_id")Long categoryId){
//        Pageable pageable = new ProductPageDTO().getPageable(pageNo);
//        Page<ProductProjection>productProjections = productService.getAllByCategoryId(categoryId,pageable);
//        return ApiResponse.setResponse(HTTPStatus.FOUND, true, productProjections, "all products given under one category");
//    }

    @PostMapping
    public ApiResponse<?> create(@RequestBody Product product) {
        productService.create(product);
        return ApiResponse.setResponse(HTTPStatus.CREATED, true, null, "successfully created product");
    }




    @GetMapping("/{id}")
    public ApiResponse<?> getOne(@PathVariable("id") Long id) {
        Optional<ProductProjection> productProjection = productService.getOne(id);
        return ApiResponse.setResponse(HTTPStatus.FOUND, true, productProjection, "product information fetched" );
    }







}
