/**
 * Author: Rifat Shariar Sakil
 * Time: 8:44 PM
 * Date: 12/3/2024
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.controller;


import com.ekhonni.backend.dto.CategoryDTO;
import com.ekhonni.backend.enums.HTTPStatus;
import com.ekhonni.backend.response.ApiResponse;
import com.ekhonni.backend.service.CategoryService;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v2/category")
@RestController
public record CategoryController(CategoryService categoryService) {


    // done
    @PostMapping
    public ApiResponse<?> createCategory(@RequestBody CategoryDTO categoryDTO) {
        try {
            categoryService.save(categoryDTO);
            return new ApiResponse<>(HTTPStatus.CREATED, null);
        } catch (RuntimeException e) {
            return new ApiResponse<>(HTTPStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            return new ApiResponse<>(HTTPStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred.");
        }
    }


    //done
    @GetMapping("/{name}")
    public ApiResponse<?> getSubCategories(@PathVariable String name) {
        try {
            return new ApiResponse<>(HTTPStatus.FOUND, categoryService.getSubCategories(name));
        } catch (RuntimeException e) {
            return new ApiResponse<>(HTTPStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            return new ApiResponse<>(HTTPStatus.INTERNAL_SERVER_ERROR, null);
        }

    }

    @GetMapping("/shop-by-category")
    public ApiResponse<?> shopByCategoryList() {
        return new ApiResponse<>(HTTPStatus.FOUND, categoryService.getShopByCategoryList());
    }

    //
//    @GetMapping("/featured")
//    public ApiResponse<?> getFeatured() {
//        List<CategoryProjection> categoryProjections = categoryService.getFeatured();
//        return ApiResponse.setResponse(HTTPStatus.FOUND, true, categoryProjections, "featured categories");
//    }
//
    @DeleteMapping("/{name}")
    public ApiResponse<?> delete(@PathVariable String name) {

        try {
            categoryService.delete(name);
            return new ApiResponse<>(HTTPStatus.DELETED, null);
        } catch (Exception e) {
            return new ApiResponse<>(HTTPStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }


}
