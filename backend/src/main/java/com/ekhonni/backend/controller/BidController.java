package com.ekhonni.backend.controller;

import com.ekhonni.backend.dto.BidCreateDTO;
import com.ekhonni.backend.response.ApiResponse;
import com.ekhonni.backend.service.BidService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author: Asif Iqbal
 * Date: 12/22/24
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api/v2/bid")
public class BidController {

    private final BidService bidService;

    @PostMapping
    public ApiResponse<?> create(@Valid @RequestBody BidCreateDTO bidCreateDTO) {
        bidService.create(bidCreateDTO);
        return new ApiResponse<>(true, "Success", null, HttpStatus.CREATED);
    }

}
