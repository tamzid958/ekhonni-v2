package com.ekhonni.backend.controller;


import com.ekhonni.backend.dto.BidCreateDTO;
import com.ekhonni.backend.enums.HTTPStatus;
import com.ekhonni.backend.projection.BidProjection;
import com.ekhonni.backend.response.ApiResponse;
import com.ekhonni.backend.service.BidService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2/bid")
@AllArgsConstructor
@Tag(name = "Bid", description = "Manage bid operations")
public class BidController {

    BidService bidService;

    @PostMapping()
    public ApiResponse<?> create(@Valid @RequestBody BidCreateDTO bidCreateDTO) {
        return new ApiResponse<>(HTTPStatus.CREATED, bidService.create(bidCreateDTO));
    }

    @GetMapping("/{id}")
    public ApiResponse<?> get(@PathVariable Long id) {
        return new ApiResponse<>(HTTPStatus.ACCEPTED, bidService.get(id, BidProjection.class)) ;
    }

    @GetMapping
    public ApiResponse<?> getAll(Pageable pageable) {
        return new ApiResponse<>(HTTPStatus.ACCEPTED, bidService.getAll(BidProjection.class, pageable));
    }

    @GetMapping("/{product_id}")
    public ApiResponse<?> getAllBidsForProduct(@PathVariable("product_id") Long productId, Pageable pageable) {
        return new ApiResponse<>(HTTPStatus.ACCEPTED, bidService.getAllBidsForProduct(productId, pageable));
    }

    @PutMapping("/{id}/update")
    public ApiResponse<?> update(@PathVariable Long id, @RequestBody BidCreateDTO bidCreateDTO){
        return new ApiResponse<>(HTTPStatus.ACCEPTED, bidService.update(id, bidCreateDTO));
    }

}
