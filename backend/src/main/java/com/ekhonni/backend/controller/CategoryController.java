/**
 * Author: Rifat Shariar Sakil
 * Time: 8:44 PM
 * Date: 12/3/2024
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.controller;


import com.ekhonni.backend.dto.category.CategoryTreeDTO;
import com.ekhonni.backend.enums.HTTPStatus;
import com.ekhonni.backend.response.ApiResponse;
import com.ekhonni.backend.service.CategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/v2/category")
@RestController
public record CategoryController(CategoryService categoryService) {


    @GetMapping("/{name}/subcategories")
    public ApiResponse<?> getSubCategories(@PathVariable("name") String name) {
        return new ApiResponse<>(HTTPStatus.FOUND, categoryService.getSub(name));
    }


    @GetMapping("/tree/{user_id}")
    public ApiResponse<?> getUserCategoryTree(@PathVariable("user_id") String userId) {
        return new ApiResponse<>(HTTPStatus.FOUND, categoryService.getUserCategoryTree(UUID.fromString(userId)));
    }

    @GetMapping("/all/{user_id}")
    public ApiResponse<?> getCategoryTreeByUser(@PathVariable("user_id") String userId) {
        return new ApiResponse<>(HTTPStatus.FOUND, categoryService.findRootCategoriesBySeller(UUID.fromString(userId)));
    }

    @GetMapping("/all")
    public ApiResponse<?> getAll() {
        return new ApiResponse<>(HTTPStatus.FOUND, categoryService.getAllCategorySubCategoryDTO());
    }

    @GetMapping("/tree")
    public List<CategoryTreeDTO> getCategoryTree() {
        return categoryService.getCategoryTree();
    }

    @GetMapping("/top")
    public ApiResponse<?> getTop() {
        return new ApiResponse<>(HTTPStatus.FOUND, categoryService.getTopCategories());
    }

}
