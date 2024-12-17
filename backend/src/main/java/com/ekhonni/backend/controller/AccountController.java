/*
    Author: Asif Iqbal
 */

package com.ekhonni.backend.controller;

import com.ekhonni.backend.dto.AccountDTO;
import com.ekhonni.backend.model.Transaction;
import com.ekhonni.backend.response.ApiResponse;
import com.ekhonni.backend.service.AccountService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v2/account")
@Tag(name = "Account", description = "Manage account operations")
public record AccountController(AccountService accountService) {

    @GetMapping
    public ApiResponse<?> getAll() {
        return new ApiResponse<>(true, "Success", accountService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ApiResponse<?> get(@PathVariable Long id) {
        return new ApiResponse<>(true, "Success", accountService.get(id), HttpStatus.OK);
    }

    @GetMapping("/{id}/user")
    public ApiResponse<?> getUser(@PathVariable("id") Long id) {
        return new ApiResponse<>(true, "Success", accountService.getUser(id), HttpStatus.OK);
    }

    @GetMapping("/deleted")
    public ApiResponse<?> getAllDeleted() {
        return new ApiResponse<>(true, "Success", accountService.getAllDeleted(), HttpStatus.OK);
    }

    @GetMapping("/including-deleted")
    public ApiResponse<?> getAllIncludingDeleted() {
        return new ApiResponse<>(true, "Success", accountService.getAllIncludingDeleted(), HttpStatus.OK);
    }

    @PostMapping("/{user_id}")
    public ApiResponse<?> create(@PathVariable("user_id") UUID userId) {
        return new ApiResponse<>(true, "Success", accountService.create(userId), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ApiResponse<?> update(@PathVariable("id") Long id, @Valid @RequestBody AccountDTO accountDto) {
        return new ApiResponse<>(true, "Success", accountService.update(id, accountDto), HttpStatus.OK);
    }

    @GetMapping("/{id}/balance")
    public ApiResponse<?> getBalance(@PathVariable("id") Long id) {
        return new ApiResponse<>(true, "Success", accountService.getBalance(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<?> softDelete(@PathVariable("id") Long id) {
        accountService.softDelete(id);
        return new ApiResponse<>(true, "Success", null, HttpStatus.OK);
    }

    @DeleteMapping("/")
    public ApiResponse<?> softDeleteSelected(@RequestBody List<Long> ids) {
        accountService.softDelete(ids);
        return new ApiResponse<>(true, "Success", null, HttpStatus.OK);
    }

    @DeleteMapping("/{id}/delete-permanently")
    public ApiResponse<?> delete(@PathVariable("id") Long id) {
        accountService.delete(id);
        return new ApiResponse<>(true, "Success", null, HttpStatus.OK);
    }

    @PatchMapping("/{id}/restore")
    public ApiResponse<?> restore(@PathVariable("id") Long id) {
        accountService.restore(id);
        return new ApiResponse<>(true, "Success", null, HttpStatus.OK);
    }

    @PatchMapping("/restore")
    public ApiResponse<?> restoreSelected(@RequestBody List<Long> ids) {
        accountService.restore(ids);
        return new ApiResponse<>(true, "Success", null, HttpStatus.OK);
    }

    @PatchMapping("/restore-all")
    public ApiResponse<?> restoreAll() {
        accountService.restore();
        return new ApiResponse<>(true, "Success", null, HttpStatus.OK);
    }

    public List<Transaction> getAllTransactions() {
        return null;
    }

}
