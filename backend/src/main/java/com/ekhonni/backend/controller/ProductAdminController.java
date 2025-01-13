/**
 * Author: Rifat Shariar Sakil
 * Time: 11:45 PM
 * Date: 1/12/2025
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.controller;

import com.ekhonni.backend.enums.HTTPStatus;
import com.ekhonni.backend.response.ApiResponse;
import com.ekhonni.backend.service.ProductAdminService;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2/admin/product")
public record ProductAdminController(ProductAdminService productAdminService) {
    @GetMapping("/pending")
    public ApiResponse<?> getPendingPosts(Pageable pageable) {
        return new ApiResponse<>(HTTPStatus.FOUND, productAdminService.getAllPending(pageable));
    }

    @GetMapping("/pending/{id}")
    public ApiResponse<?> getOnePendingPost(@PathVariable Long id) {
        return new ApiResponse<>(HTTPStatus.FOUND, productAdminService.getOnePending(id));
    }

    @PatchMapping("/pending/{id}/approve")
    public ApiResponse<?> approveOnePost(@PathVariable Long id) {
        return new ApiResponse<>(HTTPStatus.OK, productAdminService.approveOne(id));
    }

    @PatchMapping("/pending/{id}/decline")
    public ApiResponse<?> declineOnePost(@PathVariable Long id) {
        return new ApiResponse<>(HTTPStatus.DELETED, productAdminService.declineOne(id));
    }

//    @DeleteMapping("/{id}")
//    public ApiResponse<?> deleteOnePost(@PathVariable Long id) {
//        return new ApiResponse<>(HTTPStatus.DELETED, productAdminService.deleteOne(id));
//    }
}
