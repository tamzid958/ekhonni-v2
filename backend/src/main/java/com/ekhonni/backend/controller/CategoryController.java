/**
 * Author: Rifat Shariar Sakil
 * Time: 8:44 PM
 * Date: 12/3/2024
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.controller;



import com.ekhonni.backend.enums.HTTPStatus;
import com.ekhonni.backend.model.Category;
import com.ekhonni.backend.projection.CategoryProjection;
import com.ekhonni.backend.response.ApiResponse;
import com.ekhonni.backend.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/category")
@RestController
public record CategoryController(CategoryService categoryService) {




    @PostMapping
    public ApiResponse<?> create(@RequestBody Category category) {
        Category savedCategory = categoryService.save(category);
        return ApiResponse.setResponse(HTTPStatus.CREATED, true, null, "Category Created");

    }



    @GetMapping("/{id}")
    public ApiResponse<?> getSub(@PathVariable Long id) {
         List<CategoryProjection> categoryProjection = categoryService.getSub(id);
         return ApiResponse.setResponse(HTTPStatus.FOUND, true, categoryProjection, "Category Tree given");
    }

    @GetMapping("/featured")
    public ApiResponse<?> getFeatured(){
        List<CategoryProjection> categoryProjections = categoryService.getFeatured();
        return ApiResponse.setResponse(HTTPStatus.FOUND, true, categoryProjections, "featured categories");
    }

    @DeleteMapping("/{id}")
    public ApiResponse<?> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return ApiResponse.setResponse(HTTPStatus.DELETED, true, null, "successfully deleted");
    }




}
