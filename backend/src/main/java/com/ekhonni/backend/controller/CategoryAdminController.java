/**
 * Author: Rifat Shariar Sakil
 * Time: 12:13 PM
 * Date: 2/5/25
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.controller;


import com.ekhonni.backend.dto.category.CategoryCreateDTO;
import com.ekhonni.backend.dto.category.CategoryTreeDTO;
import com.ekhonni.backend.dto.category.CategoryUpdateDTO;
import com.ekhonni.backend.dto.product.ProductCreateDTO;
import com.ekhonni.backend.enums.HTTPStatus;
import com.ekhonni.backend.response.ApiResponse;
import com.ekhonni.backend.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RequestMapping("/api/v2/admin/category")
@RestController
public record CategoryAdminController(CategoryService categoryService) {


    @PostMapping
    @Operation(
            summary = "Create a new category",
            description = "creates a new category using the provided details, including image",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Category creation form with image and details"
            )
    )
    public ApiResponse<?> create(
            @Parameter(description = "Category details for creation")
            @Valid @ModelAttribute CategoryCreateDTO dto) {
        return new ApiResponse<>(HTTPStatus.CREATED, categoryService.save(dto));
    }


    @GetMapping("/all")
    public ApiResponse<?> getAll() {
        return new ApiResponse<>(HTTPStatus.FOUND, categoryService.getAllCategorySubCategoryDTO());
    }


    @GetMapping("/{name}/subcategories")
    public ApiResponse<?> getSubCategories(@PathVariable("name") String name) {
        return new ApiResponse<>(HTTPStatus.FOUND, categoryService.getSub(name));
    }


    @DeleteMapping("/{name}")
    public ApiResponse<?> delete(@PathVariable String name) {
        categoryService.delete(name);
        return new ApiResponse<>(HTTPStatus.DELETED, null);
    }

    @PatchMapping("/{name}")
    @Operation(
            summary = "Update an existing category",
            description = "updates a new category using the provided details, may include image",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Category update form with image and details"
            )
    )
    public ApiResponse<?> update(
            @PathVariable String name,
            @Parameter(description = "Category details for update")
            @Valid @ModelAttribute CategoryUpdateDTO dto) {
        categoryService.update(dto,name);
        return new ApiResponse<>(HTTPStatus.ACCEPTED, null);

    }

    @GetMapping("/tree")
    public List<CategoryTreeDTO> getCategoryTree() {
        return categoryService.getCategoryTree();
    }

}

