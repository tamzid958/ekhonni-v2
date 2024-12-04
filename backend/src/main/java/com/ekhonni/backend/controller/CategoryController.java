/**
 * Author: Rifat Shariar Sakil
 * Time: 8:44 PM
 * Date: 12/3/2024
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.controller;


import com.ekhonni.backend.model.Category;
import com.ekhonni.backend.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/ekhonni-v2/category")
@RestController
public record CategoryController(CategoryService categoryService) {

    @GetMapping
    public ResponseEntity<List<Category>> getAll() {
        List<Category> categories = categoryService.getAll();
        return ResponseEntity.ok(categories);
    }

    @PostMapping
    public Category createCategory(@RequestBody Category category) {
       return categoryService.create(category);
    }









}
