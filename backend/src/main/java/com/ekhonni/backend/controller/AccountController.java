/*
    Author: Asif Iqbal
 */

package com.ekhonni.backend.controller;

import com.ekhonni.backend.dto.AccountDTO;
import com.ekhonni.backend.model.Transaction;
import com.ekhonni.backend.projection.AccountProjection;
import com.ekhonni.backend.response.ApiResponse;
import com.ekhonni.backend.service.AccountService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import com.ekhonni.backend.enums.HTTPStatus;

@RestController
@RequestMapping("/api/v2/account")
@Tag(name = "Account", description = "Manage account operations")
public record AccountController(AccountService accountService) {

    @GetMapping
    public ApiResponse<?> getAll() {
        return new ApiResponse<>(HTTPStatus.FOUND, accountService.getAll(AccountProjection.class));
    }

    @GetMapping("/{id}")
    public ApiResponse<?> get(@PathVariable Long id) {
        return new ApiResponse<>(HTTPStatus.FOUND, accountService.get(id));
    }

    @GetMapping("/{id}/user")
    public ApiResponse<?> getUser(@PathVariable("id") Long id) {
        return new ApiResponse<>(HTTPStatus.FOUND, accountService.getUser(id));
    }

    @GetMapping("/deleted")
    public ApiResponse<?> getAllDeleted() {
        return new ApiResponse<>(HTTPStatus.FOUND, accountService.getAllDeleted());
    }

    @GetMapping("/including-deleted")
    public ApiResponse<?> getAllIncludingDeleted() {
        return new ApiResponse<>(HTTPStatus.FOUND, accountService.getAllIncludingDeleted());
    }


    @PatchMapping("/{id}")
    public ApiResponse<?> update(@PathVariable("id") Long id, @Valid @RequestBody AccountDTO accountDto) {
        return new ApiResponse<>(HTTPStatus.ACCEPTED, accountService.update(id, accountDto));
    }

    @GetMapping("/{id}/balance")
    public ApiResponse<?> getBalance(@PathVariable("id") Long id) {
        return new ApiResponse<>(HTTPStatus.FOUND, accountService.getBalance(id));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<?> softDelete(@PathVariable("id") Long id) {
        accountService.softDelete(id);
        return new ApiResponse<>(HTTPStatus.FOUND, null);
    }

    @DeleteMapping("/")
    public ApiResponse<?> softDelete(@RequestBody List<Long> ids) {
        accountService.softDelete(ids);
        return new ApiResponse<>(HTTPStatus.FOUND, null);
    }

    @DeleteMapping("/{id}/delete-permanently")
    public ApiResponse<?> delete(@PathVariable("id") Long id) {
        accountService.deletePermanently(id);
        return new ApiResponse<>(HTTPStatus.FOUND, null);
    }

    @PatchMapping("/{id}/restore")
    public ApiResponse<?> restore(@PathVariable("id") Long id) {
        accountService.restore(id);
        return new ApiResponse<>(HTTPStatus.FOUND, null);
    }

    @PatchMapping("/restore")
    public ApiResponse<?> restore(@RequestBody List<Long> ids) {
        accountService.restore(ids);
        return new ApiResponse<>(HTTPStatus.FOUND, null);
    }

    @PatchMapping("/restore-all")
    public ApiResponse<?> restoreAll() {
        accountService.restoreAll();
        return new ApiResponse<>(HTTPStatus.FOUND, null);
    }

    public List<Transaction> getAllTransactions() {
        return null;
    }
}