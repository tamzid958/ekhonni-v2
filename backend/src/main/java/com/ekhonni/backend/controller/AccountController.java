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

@RestController
@RequestMapping("/api/v2/account")
public record AccountController(AccountService accountService) {

    @GetMapping("/get")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(accountService.getAll());
    }

    @GetMapping("/get-including-deleted")
    public ResponseEntity<?> getAllIncludingDeleted() {
        return ResponseEntity.ok(accountService.getAllIncludingDeleted());
    }


    @GetMapping("/{id}/balance")
    public ResponseEntity<?> getBalance(@PathVariable("id") Long id) {
        return new ResponseEntity<>(accountService.getBalance(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> softDelete(@PathVariable("id") Long id) {
        accountService.softDelete(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/hard-delete")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        accountService.delete(id);
        return ResponseEntity.noContent().build();
    }

    public List<Transaction> getAllTransactions() {
        return null;
    }
}
