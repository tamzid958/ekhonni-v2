package com.ekhonni.backend.controller;

import com.ekhonni.backend.dto.*;
import com.ekhonni.backend.enums.HTTPStatus;
import com.ekhonni.backend.model.AuthToken;
import com.ekhonni.backend.model.Report;
import com.ekhonni.backend.projection.UserProjection;
import com.ekhonni.backend.projection.bid.SellerBidProjection;
import com.ekhonni.backend.response.ApiResponse;
import com.ekhonni.backend.service.BidService;
import com.ekhonni.backend.service.ProductService;
import com.ekhonni.backend.service.ReportService;
import com.ekhonni.backend.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.UUID;

/**
 * Author: Md Jahid Hasan
 */

@RestController
@RequestMapping("/api/v2/user")
@AllArgsConstructor
@Validated
public class UserController {

    private final UserService userService;
    private final BidService bidService;
    private final ProductService productService;
    private final ReportService reportService;

    @GetMapping("/{id}")
    public UserProjection getById(@PathVariable UUID id) {
        return userService.get(id, UserProjection.class);
    }


    @PatchMapping("/{id}")
    @PreAuthorize("#id == authentication.principal.id && @userService.isActive(#id)")
    public UserUpdateDTO update(@PathVariable UUID id, @Valid @RequestBody UserUpdateDTO userUpdateDTO) {
        return userService.update(id, userUpdateDTO);
    }

    @PatchMapping("/{id}/change-email")
    @PreAuthorize("#id == authentication.principal.id && @userService.isActive(#id)")
    public String updateEmail(@PathVariable UUID id, @Valid @RequestBody EmailDTO emailDTO) {
        return userService.updateEmail(id, emailDTO);
    }

    @PatchMapping("/{id}/change-password")
    @PreAuthorize("#id == authentication.principal.id && @userService.isActive(#id)")
    public String updatePassword(@PathVariable UUID id, @Valid @RequestBody PasswordDTO passwordDTO) {
        return userService.updatePassword(id, passwordDTO);
    }

    @PatchMapping("/{id}/image")
    @PreAuthorize("#id == authentication.principal.id && @userService.isActive(#id)")
    public String upload(@PathVariable UUID id, @Valid @ModelAttribute ProfileImageDTO profileImageDTO) throws IOException {
        return userService.upload(id, profileImageDTO);
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("#id == authentication.principal.id && @userService.isActive(#id)")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        userService.softDelete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/refresh-token")
    @PreAuthorize("#id == authentication.principal.id && @userService.isActive(#id)")
    public AuthToken getNewAccessToken(@PathVariable UUID id, @RequestBody RefreshTokenDTO refreshTokenDTO) {
        return userService.getNewAccessToken(refreshTokenDTO);
    }


    @PostMapping("/{id}/report")
    public ResponseEntity<Report> createReport(@PathVariable UUID reporterId, @RequestBody ReportDTO reportDTO) {
        Report savedReport = reportService.createReport(reporterId, reportDTO);
        return ResponseEntity.ok(savedReport);
    }

    @GetMapping("/{id}/reports")
    public ResponseEntity<Page<Report>> getReportsByUser(@PathVariable UUID userId, Pageable pageable) {
        return ResponseEntity.ok(reportService.getAllReportsByUser(userId, pageable));
    }

    @GetMapping("/{id}/product/{product_id}/bid")
    @PreAuthorize("#id == authentication.principal.id && #id == @productService.getSellerId(#productId)")
    public ApiResponse<?> getAllBidsForProduct(@PathVariable UUID id,
                                               @PathVariable("product_id") Long productId,
                                               Pageable pageable) {
        return new ApiResponse<>(HTTPStatus.ACCEPTED,
                bidService.getAllForProduct(productId, SellerBidProjection.class, pageable));
    }

}
