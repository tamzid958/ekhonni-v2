package com.ekhonni.backend.enums;

/**
 * Author: Asif Iqbal
 * Date: 12/18/24
 */
public enum TransactionStatus {
    PENDING,
    TRANSFERRING,
    VALID,
    INVALID_TRANSACTION,
    SUCCESS,
    FAILED,
    CANCELLED,
    UNATTEMPTED,
    EXPIRED,
    VALIDATED,
    INITIATION_FAILED,
    NO_RESPONSE,
    VALID_WITH_RISK,
    SIGNATURE_MISMATCH,
    PARAMETERS_MISMATCH,
    PENDING_REFUND,
    REFUNDED
}
