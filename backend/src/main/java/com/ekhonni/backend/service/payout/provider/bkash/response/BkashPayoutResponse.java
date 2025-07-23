package com.ekhonni.backend.service.payout.provider.bkash.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Author: Asif Iqbal
 * Date: 2/3/25
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BkashPayoutResponse {

    @JsonProperty("completedTime")
    private String completedTime;

    @JsonProperty("trxID")
    private String trxId;

    @JsonProperty("transactionStatus")
    private String transactionStatus;

    @JsonProperty("amount")
    private Double amount;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("receiverMSISDN")
    private String receiverMsisdn;

    @JsonProperty("merchantInvoiceNumber")
    private String merchantInvoiceNumber;

    @JsonProperty("b2cFee")
    private Double b2cFee;

    public LocalDateTime getCompletedTimeAsDateTime() {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss:SSS z");
            return LocalDateTime.parse(completedTime, formatter);
        } catch (Exception e) {
            return null;
        }
    }

}
