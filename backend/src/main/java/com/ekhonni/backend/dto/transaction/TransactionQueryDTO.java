package com.ekhonni.backend.dto.transaction;

/**
 * Author: Asif Iqbal
 * Date: 1/23/25
 */
public record TransactionQueryDTO(
        Long id,
        Double amount,
        String status
) {
}
