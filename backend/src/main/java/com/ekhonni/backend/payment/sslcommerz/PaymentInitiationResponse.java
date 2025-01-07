package com.ekhonni.backend.payment.sslcommerz;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Author: Asif Iqbal
 * Date: 1/4/25
 */
@AllArgsConstructor
@Getter
public class PaymentInitiationResponse {
    private String gatewayPageURL;
}
