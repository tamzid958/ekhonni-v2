package com.ekhonni.backend.controller;


import com.ekhonni.backend.dto.BidLogCreateDTO;
import com.ekhonni.backend.dto.BidLogResponseDTO;
import com.ekhonni.backend.model.BidLog;
import com.ekhonni.backend.projection.BidLogProjection;
import com.ekhonni.backend.service.BidLogService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v2/bidlog")
public record BidLogController(BidLogService bidLogService) {

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public BidLogResponseDTO create(@Valid @RequestBody BidLogCreateDTO bidLogCreateDTO) {
        return bidLogService.create(bidLogCreateDTO);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @GetMapping("/{id}")
    public BidLog getByID(@PathVariable Long id){
        return bidLogService.get(id);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @GetMapping
    public List<BidLogProjection> getAll(){
        return bidLogService.getAll(BidLogProjection.class);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping("/{id}/update")
    public BidLogCreateDTO update(@PathVariable Long id, @RequestBody BidLogCreateDTO bidLogCreateDTO){
        return bidLogService.update(id, bidLogCreateDTO);
    }


}
