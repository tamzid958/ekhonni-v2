/**
 * Author: Rifat Shariar Sakil
 * Time: 8:44 PM
 * Date: 12/3/2024
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.controller;


import com.ekhonni.backend.model.Category;
import com.ekhonni.backend.model.Product;
import com.ekhonni.backend.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/category")
@RestController
public record CategoryController(CategoryService categoryService) {



    @PostMapping
    public ResponseEntity<?> create(@RequestBody Category category) {
        Category savedCategory = categoryService.save(category);
        return ResponseEntity.status(HttpStatus.CREATED).body("category created");
    }


    @GetMapping
    public List<Category> getAll(){
        return categoryService.getAll();
    }


//    @GetMapping
//    public ResponseEntity<List<CategoryDTO>> getAll(){
//        List<CategoryDTO> categoryDTOs = categoryService.getAll();
//        return ResponseEntity.ok(categoryDTOs);
//    }
//
//    @GetMapping("/{id}")
//    public CategoryDTO getById(@PathVariable Long id) {
//        return categoryService.getById(id);
//    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> delete(@PathVariable Long id) {
//        categoryService.delete(id);
//        return ResponseEntity.noContent().build();
//    }

    //    @PatchMapping("/{id}")
    //    update information









}
