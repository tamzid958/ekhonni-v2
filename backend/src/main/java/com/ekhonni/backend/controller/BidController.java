package com.ekhonni.backend.controller;


import com.ekhonni.backend.dto.BidCreateDTO;
import com.ekhonni.backend.dto.BidResponseDTO;
import com.ekhonni.backend.enums.HTTPStatus;
import com.ekhonni.backend.exception.BidNotFoundException;
import com.ekhonni.backend.model.Bid;
import com.ekhonni.backend.projection.BidProjection;
import com.ekhonni.backend.response.ApiResponse;
import com.ekhonni.backend.service.BidService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v2/bid")
@AllArgsConstructor
@Tag(name = "Bid", description = "Manage bid operations")
public class BidController {

    BidService bidService;

    @PostMapping()
    @PreAuthorize("isAuthenticated()")
    public ApiResponse<BidResponseDTO> create(@Valid @RequestBody BidCreateDTO bidCreateDTO) {
        return new ApiResponse<>(HTTPStatus.CREATED, bidService.create(bidCreateDTO));
    }

    @GetMapping("/{id}")
    public ApiResponse<BidProjection> get(@PathVariable Long id) {
        return new ApiResponse<>(HTTPStatus.ACCEPTED, bidService.get(id, BidProjection.class)) ;
    }

    @GetMapping
    public ApiResponse<List<BidProjection>> getAll(){
        return new ApiResponse<>(HTTPStatus.ACCEPTED, bidService.getAll(BidProjection.class));
    }

    @PutMapping("/{id}/update")
    public BidCreateDTO update(@PathVariable Long id, @RequestBody BidCreateDTO bidCreateDTO){
        return bidService.update(id, bidCreateDTO);
    }

}
