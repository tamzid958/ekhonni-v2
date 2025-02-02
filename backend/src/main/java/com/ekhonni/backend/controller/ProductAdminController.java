/**
 * Author: Rifat Shariar Sakil
 * Time: 11:45 PM
 * Date: 1/12/2025
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.controller;

import com.ekhonni.backend.enums.HTTPStatus;
import com.ekhonni.backend.filter.AdminProductFilter;
import com.ekhonni.backend.filter.UserProductFilter;
import com.ekhonni.backend.response.ApiResponse;
import com.ekhonni.backend.service.ProductAdminService;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2/admin/product")
public record ProductAdminController(ProductAdminService productAdminService) {
    @GetMapping("/pending")
    public ApiResponse<?> getAll(Pageable pageable) {
        return new ApiResponse<>(HTTPStatus.FOUND, productAdminService.getAll(pageable));
    }

    @GetMapping("/pending/{id}")
    public ApiResponse<?> getOne(@PathVariable Long id) {
        return new ApiResponse<>(HTTPStatus.FOUND, productAdminService.getOne(id));
    }

    @PatchMapping("/pending/{id}/approve")
    public ApiResponse<?> approveOne(@PathVariable Long id) {
        return new ApiResponse<>(HTTPStatus.OK, productAdminService.approveOne(id));
    }

    @PatchMapping("/pending/{id}/decline")
    public ApiResponse<?> declineOne(@PathVariable Long id) {
        return new ApiResponse<>(HTTPStatus.DELETED, productAdminService.declineOne(id));
    }

    @DeleteMapping("/approve/{id}")
    public ApiResponse<?> deleteOne(@PathVariable Long id) {
        return new ApiResponse<>(HTTPStatus.DELETED, productAdminService.deleteOne(id));
    }


    @GetMapping("/filter")
    public ApiResponse<?> getFilteredForAdmin(@ModelAttribute AdminProductFilter filter) {
        //System.out.println(filter.getCategoryName());
        return new ApiResponse<>(HTTPStatus.FOUND, productAdminService.getAllFilteredForAdmin(filter));
    }
}
