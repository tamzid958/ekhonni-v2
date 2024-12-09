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
        //return ResponseEntity.status(HttpStatus.CREATED).body("category created");
    }


    @GetMapping
    public ApiResponse<?> getAll(){
        List<CategoryProjection> categoryProjections =  categoryService.getAll();
        return ApiResponse.setResponse(HTTPStatus.FOUND, true, categoryProjections, "Categories retrieved successfully");
    }

    @GetMapping("/{id}")
    public ApiResponse<?> getOne(@PathVariable Long id) {
         CategoryProjection categoryProjection = categoryService.getOne(id);
         return ApiResponse.setResponse(HTTPStatus.FOUND, true, categoryProjection, "Category Tree given");
    }

//    @GetMapping
//    public ApiResponse<List<CategoryDTO>> getAll(){
//       return new ApiResponse<>(true, "ok", categoryService.getAll(), HttpStatus.);
//    }
//

//    @GetMapping
//    public ResponseEntity<List<CategoryDTO>> getAll(){
//        List<CategoryDTO> categoryDTOs = categoryService.getAll();
//        return ResponseEntity.ok(categoryDTOs);
//    }
//


//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> delete(@PathVariable Long id) {
//        categoryService.delete(id);
//        return ResponseEntity.noContent().build();
//    }

    //    @PatchMapping("/{id}")
    //    update information









}
