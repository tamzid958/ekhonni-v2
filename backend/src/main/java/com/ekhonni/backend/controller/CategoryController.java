/**
 * Author: Rifat Shariar Sakil
 * Time: 8:44 PM
 * Date: 12/3/2024
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.controller;


import com.ekhonni.backend.dto.CategoryDTO;
import com.ekhonni.backend.dto.CategoryUpdateDTO;
import com.ekhonni.backend.enums.HTTPStatus;
import com.ekhonni.backend.response.ApiResponse;
import com.ekhonni.backend.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v2/category")
@RestController
public record CategoryController(CategoryService categoryService) {


    @PostMapping
    public ApiResponse<?> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        categoryService.save(categoryDTO);
        return new ApiResponse<>(HTTPStatus.CREATED, null);
    }


    @GetMapping("/all")
    public ApiResponse<?> getAllCategories() {
        return new ApiResponse<>(HTTPStatus.FOUND, categoryService.getAllCategorySubCategoryDTO());
    }


    @GetMapping("/{name}/subCategories")
    public ApiResponse<?> getSubCategories(@PathVariable("name") String name) {
        return new ApiResponse<>(HTTPStatus.FOUND, categoryService.getSub(name));
    }


    @DeleteMapping("/{name}")
    public ApiResponse<?> delete(@PathVariable String name) {
        categoryService.delete(name);
        return new ApiResponse<>(HTTPStatus.DELETED, null);
    }

    @PatchMapping("/{name}")
    public ApiResponse<?> updateCategory(@RequestBody CategoryUpdateDTO categoryUpdateDTO) {
        categoryService.update(categoryUpdateDTO);
        return new ApiResponse<>(HTTPStatus.ACCEPTED, null);

    }

    @GetMapping("/sequence")
    public ApiResponse<?> getSequenceOfCategories(@RequestParam("name") String name) {
        return new ApiResponse<>(HTTPStatus.FOUND, categoryService.getSequence(name));
    }


}
