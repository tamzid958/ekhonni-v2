package com.ekhonni.backend.dto;

/**
 * Author: Asif Iqbal
 * Date: 12/15/24
 */
public record GatewayResponseDTO(String status,
                                 String sessionkey,
                                 String GatewayPageURL,
                                 String failedReason) {
}
