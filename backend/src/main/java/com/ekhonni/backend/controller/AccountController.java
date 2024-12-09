/*
    Author: Asif Iqbal
 */

package com.ekhonni.backend.controller;

import com.ekhonni.backend.dto.AccountDTO;
import com.ekhonni.backend.model.Transaction;
import com.ekhonni.backend.service.AccountService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v2/account")
@Tag(name = "Accounts", description = "Manage account operations")
public record AccountController(AccountService accountService) {

    @GetMapping("/")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(accountService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        return ResponseEntity.ok(accountService.get(id));
    }

    @GetMapping("/deleted")
    public ResponseEntity<?> getAllDeleted() {
        return ResponseEntity.ok(accountService.getAllDeleted());
    }

    @GetMapping("/including-deleted")
    public ResponseEntity<?> getAllIncludingDeleted() {
        return ResponseEntity.ok(accountService.getAllIncludingDeleted());
    }

    @PostMapping("/{user_id}")
    public ResponseEntity<?> create(@PathVariable("user_id") UUID userId) {
        return new ResponseEntity<>(accountService.create(userId), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody AccountDTO accountDto) {
        return new ResponseEntity<>(accountService.update(id, accountDto), HttpStatus.OK);
    }

    @GetMapping("/{id}/balance")
    public ResponseEntity<?> getBalance(@PathVariable("id") Long id) {
        return new ResponseEntity<>(accountService.getBalance(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}/soft-delete")
    public ResponseEntity<?> softDelete(@PathVariable("id") Long id) {
        accountService.softDelete(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/delete-permanently")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        accountService.delete(id);
        return ResponseEntity.noContent().build();
    }

    public List<Transaction> getAllTransactions() {
        return null;
    }
}
