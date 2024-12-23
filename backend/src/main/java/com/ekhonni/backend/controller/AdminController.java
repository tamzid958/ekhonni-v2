/**
 * Author: Rifat Shariar Sakil
 * Time: 2:45 PM
 * Date: 12/18/2024
 * Project Name: backend
 */

package com.ekhonni.backend.controller;

import com.ekhonni.backend.enums.HTTPStatus;
import com.ekhonni.backend.model.Category;
import com.ekhonni.backend.response.ApiResponse;
import com.ekhonni.backend.service.CategoryService;
import com.ekhonni.backend.service.ProductService;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/admin")
public record AdminController (ProductService productService, CategoryService categoryService){
//
//    @PatchMapping("/products/{id}")
//    public boolean approveProduct(@PathVariable("id") Long id){
//        return productService.approveProduct(id);
//    }
//
//    @PatchMapping("/products/{id}")
//    public String  declineProduct(@PathVariable("id") Long id){
//        productService.declineProduct(id);
//        return "product declined";
//    }
//
//    @PostMapping("/category")
//    public ApiResponse<?> createCategory(@RequestBody Category category) {
//        Category savedCategory = categoryService.save(category);
//        return ApiResponse.setResponse(HTTPStatus.CREATED, true, null, "Category Created");
//
//    }
//
//    @DeleteMapping("/category/{id}")
//    public ApiResponse<?> deleteCategory(@PathVariable Long id) {
//        categoryService.delete(id);
//        return ApiResponse.setResponse(HTTPStatus.DELETED, true, null, "successfully deleted");
//    }
//
//
//    @PatchMapping("/category/{id}")
//    public String updateCategory(@PathVariable("id")Long id){
//        //update category
//        return "updated";
//    }


    // add admin
    //




}
