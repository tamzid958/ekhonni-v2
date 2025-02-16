package com.ekhonni.backend.controller;

/*
    Author: Asif Iqbal
 */

import com.ekhonni.backend.dto.AccountUpdateDTO;
import com.ekhonni.backend.projection.account.AccountProjection;
import com.ekhonni.backend.projection.UserProjection;
import com.ekhonni.backend.projection.account.UserAccountProjection;
import com.ekhonni.backend.response.ApiResponse;
import com.ekhonni.backend.service.AccountService;
import com.ekhonni.backend.util.ResponseUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ekhonni.backend.enums.HTTPStatus;

@RestController
@RequestMapping("/api/v2/account")
@AllArgsConstructor
@Tag(name = "Account", description = "Manage account operations")
public class AccountController {

    private final AccountService accountService;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<AccountProjection>>> getAll(Pageable pageable) {
        return ResponseUtil.createResponse(HTTPStatus.OK, accountService.getAll(AccountProjection.class, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AccountProjection>> get(@PathVariable Long id) {
        return ResponseUtil.createResponse(HTTPStatus.OK, accountService.get(id, AccountProjection.class));
    }

    @GetMapping("/{id}/user")
    public ResponseEntity<ApiResponse<UserProjection>> getUser(@PathVariable("id") Long id) {
        return ResponseUtil.createResponse(HTTPStatus.OK, accountService.getUser(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<AccountUpdateDTO>> update(
            @PathVariable("id") Long id, @Valid @RequestBody AccountUpdateDTO accountUpdateDto) {
        return ResponseUtil.createResponse(HTTPStatus.OK, accountService.update(id, accountUpdateDto));
    }

    @GetMapping("/{id}/balance")
    public ResponseEntity<ApiResponse<Double>> getBalance(@PathVariable("id") Long id) {
        return ResponseUtil.createResponse(HTTPStatus.OK, accountService.getBalance(id));
    }

    @GetMapping("/user")
    public ResponseEntity<ApiResponse<UserAccountProjection>> getAuthenticatedUserAccount() {
        return ResponseUtil.createResponse(HTTPStatus.OK, accountService.getAuthenticatedUserAccount());
    }

    @GetMapping("/user/balance")
    public ResponseEntity<ApiResponse<Double>> getAuthenticatedUserBalance() {
        return ResponseUtil.createResponse(HTTPStatus.OK, accountService.getAuthenticatedUserBalance());
    }



}