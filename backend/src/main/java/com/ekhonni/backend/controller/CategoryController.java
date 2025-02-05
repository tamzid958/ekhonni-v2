/**
 * Author: Rifat Shariar Sakil
 * Time: 8:44 PM
 * Date: 12/3/2024
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.controller;


import com.ekhonni.backend.dto.CategoryCreateDTO;
import com.ekhonni.backend.dto.CategoryTreeDTO;
import com.ekhonni.backend.dto.CategoryUpdateDTO;
import com.ekhonni.backend.enums.HTTPStatus;
import com.ekhonni.backend.response.ApiResponse;
import com.ekhonni.backend.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

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

}
