package com.ekhonni.backend.controller;

import com.ekhonni.backend.dto.account.payout.PayoutAccountCreateDTO;
import com.ekhonni.backend.enums.HTTPStatus;
import com.ekhonni.backend.response.ApiResponse;
import com.ekhonni.backend.service.PayoutAccountService;
import com.ekhonni.backend.util.ResponseUtil;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@RequestMapping("/api/v2/account/payout")
public class PayoutAccountController {

    private final PayoutAccountService payoutAccountService;

    @PostMapping()
    public ResponseEntity<ApiResponse<Void>> create(@Valid @RequestBody PayoutAccountCreateDTO dto) {
        payoutAccountService.create(dto);
        return ResponseUtil.createResponse(HTTPStatus.CREATED);
    }
}
