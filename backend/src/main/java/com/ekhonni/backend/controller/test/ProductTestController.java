/**
 * Author: Rifat Shariar Sakil
 * Time: 12:03 AM
 * Date: 12/12/2024
 * Project Name: backend
 */

package com.ekhonni.backend.controller.test;



import com.ekhonni.backend.enums.HTTPStatus;
import com.ekhonni.backend.model.Product;
import com.ekhonni.backend.projection.ProductProjection;
import com.ekhonni.backend.response.ApiResponse;
import com.ekhonni.backend.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;

import java.util.Optional;


@Controller
@RequestMapping("/test/products")
public record ProductTestController(ProductService productService){


//
//    @GetMapping
//    public String getPage(Pageable pageable, Model model) {
//
//        Page<ProductProjection> productProjections = productService.getAll(pageable);
//        model.addAttribute("products", productProjections.getContent());
//        model.addAttribute("currentPage", productProjections.getNumber());
//        model.addAttribute("totalPages", productProjections.getTotalPages());
//        return "products";
//    }
//
//    @GetMapping("/by-categories/{category_id}")
//    public String getByCategoryId( @PathVariable("category_id")Long categoryId, Model model, Pageable pageable){
//        Page<ProductProjection>productProjections = productService.getAllByCategoryId(categoryId,pageable);
//        model.addAttribute("products", productProjections.getContent());
//        model.addAttribute("currentPage", productProjections.getNumber());
//        model.addAttribute("totalPages", productProjections.getTotalPages());
//        //return ApiResponse.setResponse(HTTPStatus.FOUND, true, productProjections, "all products given under one category");
//        return "products";
//    }
//
//
////    @GetMapping("/by-categories/{category_id}")
////    public ApiResponse<?> getByCategoryId(@RequestParam(name = "pageNo",required = true) Integer pageNo, @PathVariable("category_id")Long categoryId){
////        Pageable pageable = new PageDTO().getPageable(pageNo);
////        Page<ProductProjection>productProjections = productService.getAllByCategoryId(categoryId,pageable);
////        return ApiResponse.setResponse(HTTPStatus.FOUND, true, productProjections, "all products given under one category");
////    }
//
//    @PostMapping
//    public ApiResponse<?> create(@RequestBody Product product) {
//        productService.create(product);
//        return ApiResponse.setResponse(HTTPStatus.CREATED, true, null, "successfully created product");
//    }
//
//
//    @GetMapping("/{id}")
//    public ApiResponse<?> getOne(@PathVariable("id") Long id) {
//        Optional<ProductProjection> productProjection = productService.getOne(id);
//        return ApiResponse.setResponse(HTTPStatus.FOUND, true, productProjection, "product information fetched" );
//    }


}
