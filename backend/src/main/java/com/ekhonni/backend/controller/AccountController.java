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
    public ResponseEntity<?> create(@PathVariable("user_id") UUID userId) {
        accountService.create(userId);
        return new ResponseEntity<>(HttpStatus.valueOf(201));
    }

    @GetMapping("/{id}/balance")
    public double getBalance(@PathVariable("id") UUID id) {
        return 0.0;
    }

    public List<Transaction> getAllTransactions() {
        return null;
    }
}
