package com.ekhonni.backend.controller;


import com.ekhonni.backend.dto.BidLogDTO;
import com.ekhonni.backend.model.BidLog;
import com.ekhonni.backend.projection.BidLogProjection;
import com.ekhonni.backend.service.BidLogService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v2/bidlog")
public record BidLogController(BidLogService bidLogService) {

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping("/create")
    public BidLogDTO create(@RequestBody BidLogDTO bidLogDTO) {
        return bidLogService.create(bidLogDTO);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @GetMapping("/{id}")
    public BidLogProjection getByID(@PathVariable Long id){
        return bidLogService.getById(id);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @GetMapping
    public List<BidLogProjection> getAll(){
        return bidLogService.getAll();
    }
}
