/**
 * Author: Rifat Shariar Sakil
 * Time: 7:15 PM
 * Date: 2/3/25
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.controller;


import com.ekhonni.backend.enums.HTTPStatus;
import com.ekhonni.backend.response.ApiResponse;
import com.ekhonni.backend.service.WatchlistService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2/user/watchlist")
@AllArgsConstructor
public class WatchlistController {

    private final WatchlistService watchlistService;

    @PostMapping
    public ApiResponse<?> addProduct(@RequestParam("productId") Long productId){
        watchlistService.addProduct(productId);
        return new ApiResponse<>(HTTPStatus.CREATED, null);
    }


    @GetMapping
    public ApiResponse<?> getAllProducts(Pageable pageable){
        return new ApiResponse<>(HTTPStatus.FOUND, watchlistService.getAllProducts(pageable));

    }
}
