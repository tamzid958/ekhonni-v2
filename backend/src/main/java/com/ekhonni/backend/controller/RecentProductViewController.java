/**
 * Author: Rifat Shariar Sakil
 * Time: 2:30 PM
 * Date: 2/24/25
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.controller;

import com.ekhonni.backend.enums.HTTPStatus;
import com.ekhonni.backend.response.ApiResponse;
import com.ekhonni.backend.service.RecentProductViewService;
import com.ekhonni.backend.service.WatchlistService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2/product/recent-view")
@AllArgsConstructor
public class RecentProductViewController {
    private final RecentProductViewService recentProductViewService;


    @GetMapping
    public ApiResponse<?> getAllProducts(Pageable pageable) {
        return new ApiResponse<>(HTTPStatus.FOUND, recentProductViewService.getAllProducts(pageable));
    }

    @DeleteMapping
    public ApiResponse<?> removeProducts(@RequestBody List<Long> productIds) {
        return new ApiResponse<>(HTTPStatus.DELETED, recentProductViewService.removeProducts(productIds));
    }

    @DeleteMapping("/clear")
    public ApiResponse<?> clearAll(){
        return new ApiResponse<>(HTTPStatus.DELETED, recentProductViewService.clearAll());
    }
}
