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
import com.ekhonni.backend.enums.HTTPStatus;
import com.ekhonni.backend.response.ApiResponse;
import com.ekhonni.backend.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RequestMapping("/api/v2/admin/category")
@RestController
public record CategoryAdminController(CategoryService categoryService) {


    @PostMapping
    public ApiResponse<?> create(@Valid @RequestBody CategoryCreateDTO dto)  {
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
    public ApiResponse<?> update(@RequestBody CategoryUpdateDTO categoryUpdateDTO) {
        categoryService.update(categoryUpdateDTO);
        return new ApiResponse<>(HTTPStatus.ACCEPTED, null);

    }

    @GetMapping("/tree")
    public List<CategoryTreeDTO> getCategoryTree() {
        return categoryService.getCategoryTree();
    }

}

