package com.ekhonni.backend.controller;

import com.ekhonni.backend.response.ApiResponse;
import com.ekhonni.backend.service.BaseService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * Author: Asif Iqbal
 * Date: 12/17/24
 */
@AllArgsConstructor
public class BaseController<T, ID> {
    private final BaseService<T, ID> service;

    /**
     * ==========================
     * Get mapping
     * ==========================
     * Projection based, pageable
     */

    @GetMapping
    public <P> ApiResponse<?> getAll(Class<P> projection, Pageable pageable) {
        return new ApiResponse<>(true, "Success", service.getAll(projection, pageable), HttpStatus.OK);
    }

    @GetMapping("/deleted")
    public <P> ApiResponse<?> getDeleted(Class<P> projection, Pageable pageable) {
        return new ApiResponse<>(true, "Success", service.getAllDeleted(projection, pageable), HttpStatus.OK);
    }

    @GetMapping("/all-including-deleted")
    public <P> ApiResponse<?> getAllIncludingDeleted(Class<P> projection, Pageable pageable) {
        return new ApiResponse<>(true, "Success", service.getAllIncludingDeleted(projection, pageable), HttpStatus.OK);
    }

    @GetMapping("/{id}/entity")
    public <P> ApiResponse<?> get(ID id, Class<P> projection) {
        return new ApiResponse<>(true, "Success", service.get(id, projection), HttpStatus.OK);
    }

    /**
     * ==========================
     * Delete mapping
     * ==========================
     */

}
