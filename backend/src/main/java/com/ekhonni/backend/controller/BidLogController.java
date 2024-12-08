package com.ekhonni.backend.controller;


import com.ekhonni.backend.model.BidLog;
import com.ekhonni.backend.projection.BidLogProjection;
import com.ekhonni.backend.service.BidLogService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2/bidlog")
public record BidLogController(BidLogService bidLogService) {

    //create new bidLog
    @PostMapping
    public BidLog create(@RequestBody BidLog bidLog) {
        return bidLogService.create(bidLog);
    }
}
