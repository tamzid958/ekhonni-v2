package com.ekhonni.backend.response.bkash;

import lombok.*;

/**
 * Author: Asif Iqbal
 * Date: 2/3/25
 */

@Data
public class BkashPayoutResponse {
    private String completedTime;
    private String trxID;
    private String transactionStatus;
    private String amount;
    private String currency;
    private String receiverMSISDN;
    private String merchantInvoiceNumber;
    private String b2cFee;
}
