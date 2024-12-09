/**
 * Author: Rifat Shariar Sakil
 * Time: 2:12 PM
 * Date: 12/3/2024
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.controller;


import com.ekhonni.backend.dto.ProductDTO;
import com.ekhonni.backend.enums.HttpStatusCodes;
import com.ekhonni.backend.model.Product;
import com.ekhonni.backend.projection.ProductProjection;
import com.ekhonni.backend.response.ApiResponse;
import com.ekhonni.backend.service.ProductService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public record ProductController(ProductService productService){


    @GetMapping
    public ApiResponse<?> getAll(){
        List<ProductProjection>productDTOs =  productService.getAll();
        return ApiResponse.setResponse(HttpStatusCodes.FOUND, true, productDTOs, "products fetched successfully");
    }


    @GetMapping("/by-categories/{category_id}")
    public ApiResponse<?> getByCategoryId(@PathVariable("category_id") Long categoryId){
        List<ProductProjection>productDTOs = productService.getAllByCategoryId(categoryId);
        return ApiResponse.setResponse(HttpStatusCodes.FOUND, true, productDTOs, "all products given under one category");
    }

    @PostMapping
    public ApiResponse<?> create(@RequestBody Product product) {
       // return ResponseEntity.status(HttpStatus.CREATED).body(productService.create(product));
        return ApiResponse.setResponse(HttpStatusCodes.CREATED, true, null, "successfully created product");
    }

//    @GetMapping
//    public ResponseEntity<List<ProductDTO>> getAll() {
//        List<ProductDTO> productDTOs = productService.getAll();
//        return ResponseEntity.status(HttpStatus.OK).body(productDTOs);
//    }


//    @GetMapping("/{id}")
//    public ProductDTO getOne(@PathVariable("id") Long id) {
//        return productService.getOne(id);
//    }


//    @GetMapping("/by/{category_id}")
//    public ResponseEntity<List<Product>> getAllByCategory(@PathVariable("category_id") Long categoryId) {
////        System.out.println(categoryId);
//        List<Product> products = productService.getAllByCategory(categoryId);
//        return ResponseEntity.status(HttpStatus.OK).body(products);
//    }









//    @DeleteMapping("/{id}")
//    public ResponseEntity<String> deleteProduct(@PathVariable("id") Long id) {
//        boolean isDeleted = productService.delete(id);
//        if (isDeleted) return ResponseEntity.ok("Product deleted successfully");
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
//
//    }




}
