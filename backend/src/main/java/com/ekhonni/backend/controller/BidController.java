package com.ekhonni.backend.controller;


import com.ekhonni.backend.dto.BidCreateDTO;
import com.ekhonni.backend.dto.BidResponseDTO;
import com.ekhonni.backend.exception.BidNotFoundException;
import com.ekhonni.backend.model.Bid;
import com.ekhonni.backend.projection.BidProjection;
import com.ekhonni.backend.service.BidService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v2/bid")
public record BidController(BidService bidService) {

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public BidResponseDTO create(@Valid @RequestBody BidCreateDTO bidCreateDTO) {
        return bidService.create(bidCreateDTO);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @GetMapping("/{id}")
    public Bid get(@PathVariable Long id){
        return bidService.get(id).orElseThrow(BidNotFoundException::new);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @GetMapping
    public List<BidProjection> getAll(){
        return bidService.getAll(BidProjection.class);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping("/{id}/update")
    public BidCreateDTO update(@PathVariable Long id, @RequestBody BidCreateDTO bidCreateDTO){
        return bidService.update(id, bidCreateDTO);
    }


}
