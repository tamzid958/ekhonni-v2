package com.ekhonni.backend.controller;

import com.ekhonni.backend.dto.account.payout.PayoutAccountCreateDTO;
import com.ekhonni.backend.response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author: Asif Iqbal
 * Date: 2/2/25
 */
@RestController
@RequestMapping("/api/v2/account/payout")
public class PayoutAccountController {

    @PostMapping()
    public ResponseEntity<ApiResponse<Void>> create(@Valid @RequestBody PayoutAccountCreateDTO dto) {
        return null;
    }
}
