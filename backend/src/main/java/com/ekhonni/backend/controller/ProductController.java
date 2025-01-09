/**
 * Author: Rifat Shariar Sakil
 * Time: 2:12 PM
 * Date: 12/3/2024
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.controller;


import com.ekhonni.backend.dto.ProductDTO;
import com.ekhonni.backend.enums.HTTPStatus;
import com.ekhonni.backend.filter.ProductFilter;
import com.ekhonni.backend.projection.ProductProjection;
import com.ekhonni.backend.response.ApiResponse;
import com.ekhonni.backend.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v2/product")
public record ProductController(ProductService productService) {


    //done
    @GetMapping
    public ApiResponse<?> getAllProducts(Pageable pageable) {
        Page<ProductProjection> productProjections = productService.getAll(ProductProjection.class, pageable);
        return new ApiResponse<>(HTTPStatus.FOUND, productProjections);
    }

    @GetMapping("/filter")
    public ApiResponse<?> getAllProductsFiltered(@RequestBody ProductFilter productFilter) {
        List<ProductProjection> productProjections = productService.getAllFiltered(productFilter);
        return new ApiResponse<>(HTTPStatus.FOUND, productProjections);
    }


    //done (paused for now)
    @PostMapping
    public ApiResponse<?> createProduct(@ModelAttribute ProductDTO productDTO) {
        productService.create(productDTO);
        return new ApiResponse<>(HTTPStatus.CREATED, null);
    }


    //done
    @GetMapping("/{id}")
    public ApiResponse<?> getOneProduct(@PathVariable Long id) {
        ProductProjection productProjection = productService.get(id, ProductProjection.class);
        return new ApiResponse<>(HTTPStatus.FOUND, productProjection);
    }


    //done
    @PatchMapping("/{id}")
    public ApiResponse<?> updateOneProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        ProductDTO updatedProductDTO = productService.update(id, productDTO);
        return new ApiResponse<>(HTTPStatus.FOUND, updatedProductDTO);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<?> deleteOneProduct(@PathVariable("id") Long id) {
        productService.softDelete(id);
        return new ApiResponse<>(HTTPStatus.DELETED, null);
    }

    @GetMapping("/search")
    public ApiResponse<?> getSearchProducts(@RequestParam("search_text") String searchText, Pageable pageable) {
        List<ProductProjection> productProjections = productService.search(searchText, pageable);
        return new ApiResponse<>(HTTPStatus.FOUND, productProjections);
    }




    //    @GetMapping("/category/{category_name}")
//    public ApiResponse<?> getByCategoryName(@PathVariable("category_name") String categoryName, @NotNull final Pageable pageable) {
//        Page<ProductProjection> productProjections = productService.getAllByCategoryName(categoryName, pageable);
//        return new ApiResponse<>(HTTPStatus.FOUND, productProjections);
//        //   return new ApiResponse<>(HTTPStatus.FOUND, "hello");
//
//    }
//    @GetMapping("/category/filter")
//    public ApiResponse<?> getAllProductsByCategoryName(@RequestBody ProductFilterDTO productFilterDTO, @NotNull final Pageable pageable) {
//        Page<ProductProjection> productProjections = productService.getAllByCategoryName(productFilterDTO, pageable);
//        return new ApiResponse<>(HTTPStatus.FOUND, productProjections);
//        //   return new ApiResponse<>(HTTPStatus.FOUND, "hello");
//
//    }


//
//

//
//
//    @DeleteMapping("/{id}")
//    public ApiResponse<?> delete(@PathVariable("id") Long id){
//        boolean isDeleted = productService.delete(id);
//        return ApiResponse.setResponse(HTTPStatus.DELETED, isDeleted, null, "deleted");
//    }
//
//    @PostMapping("/search")
//    public ApiResponse<?> search(@RequestParam String searchText, Pageable pageable){
//        Page<ProductProjection> productProjections = productService.search(searchText,pageable);
//        return ApiResponse.setResponse(HTTPStatus.FOUND, true, productProjections, "products on search");
//    }


    //
//    @PatchMapping("/products/{id}")
//    public boolean approveProduct(@PathVariable("id") Long id){
//        return productService.approveProduct(id);
//    }
//
//    @PatchMapping("/products/{id}")
//    public String  declineProduct(@PathVariable("id") Long id){
//        productService.declineProduct(id);
//        return "product declined";
//    }
//
//    @PostMapping("/category")
//    public ApiResponse<?> createCategory(@RequestBody Category category) {
//        Category savedCategory = categoryService.save(category);
//        return ApiResponse.setResponse(HTTPStatus.CREATED, true, null, "Category Created");
//
//    }
//
//    @DeleteMapping("/category/{id}")
//    public ApiResponse<?> deleteCategory(@PathVariable Long id) {
//        categoryService.delete(id);
//        return ApiResponse.setResponse(HTTPStatus.DELETED, true, null, "successfully deleted");
//    }
//
//
//    @PatchMapping("/category/{id}")
//    public String updateCategory(@PathVariable("id")Long id){
//        //update category
//        return "updated";
//    }


}
