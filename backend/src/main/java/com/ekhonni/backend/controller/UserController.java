package com.ekhonni.backend.controller;

import com.ekhonni.backend.dto.EmailDTO;
import com.ekhonni.backend.dto.ReportDTO;
import com.ekhonni.backend.dto.user.PasswordChangeDTO;
import com.ekhonni.backend.dto.user.ProfileImageDTO;
import com.ekhonni.backend.dto.user.RefreshTokenDTO;
import com.ekhonni.backend.dto.user.UserUpdateDTO;
import com.ekhonni.backend.enums.HTTPStatus;
import com.ekhonni.backend.model.AuthToken;
import com.ekhonni.backend.projection.ReportByUserProjection;
import com.ekhonni.backend.projection.UserProjection;
import com.ekhonni.backend.projection.bid.SellerBidProjection;
import com.ekhonni.backend.response.ApiResponse;
import com.ekhonni.backend.service.BidService;
import com.ekhonni.backend.service.ProductService;
import com.ekhonni.backend.service.ReportService;
import com.ekhonni.backend.service.UserService;
import com.ekhonni.backend.util.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
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
    public ResponseEntity<ApiResponse<UserProjection>> getById(@PathVariable UUID id) {
        return ResponseUtil.createResponse(HTTPStatus.OK, userService.get(id, UserProjection.class));
    }


    @PatchMapping("/{id}")
    @PreAuthorize("#id == authentication.principal.id && @userService.isActive(#id)")
    public ResponseEntity<ApiResponse<UserUpdateDTO>> update(@PathVariable UUID id, @Valid @RequestBody UserUpdateDTO userUpdateDTO) {
        return ResponseUtil.createResponse(HTTPStatus.OK, userService.update(id, userUpdateDTO));
    }

    @PatchMapping("/{id}/change-email")
    @PreAuthorize("#id == authentication.principal.id && @userService.isActive(#id)")
    public ResponseEntity<ApiResponse<String>> updateEmail(@PathVariable UUID id, @Valid @RequestBody EmailDTO emailDTO) {
        return ResponseUtil.createResponse(HTTPStatus.OK, userService.updateEmail(id, emailDTO));
    }

    @PatchMapping("/{id}/change-password")
    @PreAuthorize("#id == authentication.principal.id && @userService.isActive(#id)")
    public ResponseEntity<ApiResponse<String>> updatePassword(@PathVariable UUID id, @Valid @RequestBody PasswordChangeDTO passwordChangeDTO) {
        return ResponseUtil.createResponse(HTTPStatus.OK, userService.updatePassword(id, passwordChangeDTO));
    }


    @Operation(
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
                            schema = @Schema(implementation = ProfileImageDTO.class)
                    )
            )
    )
    @PatchMapping("/{id}/image")
    @PreAuthorize("#id == authentication.principal.id && @userService.isActive(#id)")
    public ResponseEntity<ApiResponse<String>> upload(@PathVariable UUID id, @Valid @ModelAttribute ProfileImageDTO profileImageDTO) throws IOException {
        return ResponseUtil.createResponse(HTTPStatus.OK, userService.upload(id, profileImageDTO));
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("#id == authentication.principal.id && @userService.isActive(#id) && !@userService.isSuperAdmin(#userBlockDTO.id)")
    public ResponseEntity<ApiResponse<ResponseEntity<Object>>> delete(@PathVariable UUID id) {
        userService.softDelete(id);
        return ResponseUtil.createResponse(HTTPStatus.DELETED, ResponseEntity.noContent().build());
    }

    @PostMapping("/{id}/refresh-token")
    @PreAuthorize("#id == authentication.principal.id && @userService.isActive(#id)")
    public ResponseEntity<ApiResponse<AuthToken>> getNewAccessToken(@PathVariable UUID id, @RequestBody RefreshTokenDTO refreshTokenDTO) {
        return ResponseUtil.createResponse(HTTPStatus.OK, userService.getNewAccessToken(refreshTokenDTO));
    }


    @PostMapping("/{id}/report")
    public ResponseEntity<ApiResponse<ResponseEntity<String>>> createReport(@PathVariable("id") UUID reporterId, @Valid @RequestBody ReportDTO reportDTO) {
        reportService.createReport(reporterId, reportDTO);
        return ResponseUtil.createResponse(HTTPStatus.CREATED, ResponseEntity.ok("Report submitted successfully"));
    }

    @GetMapping("/{id}/reports")
    public ResponseEntity<ApiResponse<ResponseEntity<Page<ReportByUserProjection>>>> getReportsByUser(@PathVariable UUID id, Pageable pageable) {
        return ResponseUtil.createResponse(HTTPStatus.OK, ResponseEntity.ok(reportService.getAllReportsByUser(ReportByUserProjection.class, id, pageable)));
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
