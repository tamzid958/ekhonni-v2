/*
    Author: Asif Iqbal
 */

package com.ekhonni.backend.controller;

import com.ekhonni.backend.model.Transaction;
import com.ekhonni.backend.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v2/account")
public record AccountController(AccountService accountService) {

    @PostMapping("/{user_id}")
    public ResponseEntity<?> create(@PathVariable("user_id") UUID user_id) {
        try {
            accountService.create(user_id);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") UUID id) {
        try {
            accountService.delete(id);
            return ResponseEntity.ok("Account successfully deleted.");
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/balance")
    public double getBalance(@PathVariable("id") UUID id) {
        return 0.0;
    }

    public List<Transaction> getAllTransactions() {
        return null;
    }
}
