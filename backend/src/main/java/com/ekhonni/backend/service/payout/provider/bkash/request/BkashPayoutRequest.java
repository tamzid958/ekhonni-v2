package com.ekhonni.backend.service.payout.provider.bkash.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Author: Asif Iqbal
 * Date: 2/4/25
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BkashPayoutRequest {

    @JsonProperty("amount")
    private String amount;

    @JsonProperty("merchantInvoiceNumber")
    private String merchantInvoiceNumber;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("receiverMSISDN")
    private String receiverMsisdn;

}
