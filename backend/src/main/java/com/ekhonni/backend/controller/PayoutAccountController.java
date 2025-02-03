package com.ekhonni.backend.controller;

import com.ekhonni.backend.dto.payoutaccount.PayoutAccountCreateDTO;
import com.ekhonni.backend.dto.payoutaccount.PayoutAccountUpdateDTO;
import com.ekhonni.backend.enums.HTTPStatus;
import com.ekhonni.backend.repository.PayoutAccountProjection;
import com.ekhonni.backend.response.ApiResponse;
import com.ekhonni.backend.service.PayoutAccountService;
import com.ekhonni.backend.util.ResponseUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Author: Asif Iqbal
 * Date: 2/2/25
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api/v2/account/payout")
@Tag(name = "Payout Account", description = "Manage review payout accounts")
public class PayoutAccountController {

    private final PayoutAccountService payoutAccountService;

    @PostMapping()
    public ResponseEntity<ApiResponse<Void>> create(@Valid @RequestBody PayoutAccountCreateDTO dto) {
        payoutAccountService.create(dto);
        return ResponseUtil.createResponse(HTTPStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> update(@PathVariable Long id, @Valid @RequestBody PayoutAccountUpdateDTO dto) {
        payoutAccountService.update(id, dto);
        return ResponseUtil.createResponse(HTTPStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    @PreAuthorize("@payoutAccountService.isOwner(#id, authentication.principal.id)")
    public ResponseEntity<ApiResponse<PayoutAccountProjection>> get(@PathVariable Long id) {
        return ResponseUtil.createResponse(HTTPStatus.OK, payoutAccountService.get(id, PayoutAccountProjection.class));
    }

    public ResponseEntity<ApiResponse<Page<PayoutAccountProjection>>> getAllForUser(Pageable pageable) {
        return ResponseUtil.createResponse(HTTPStatus.OK, payoutAccountService.getAllForUser(pageable));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@payoutAccountService.isOwner(#id, authentication.principal.id)")
    public ResponseEntity<ApiResponse<Void>> update(@PathVariable Long id) {
        payoutAccountService.softDelete(id);
        return ResponseUtil.createResponse(HTTPStatus.DELETED);
    }

}
