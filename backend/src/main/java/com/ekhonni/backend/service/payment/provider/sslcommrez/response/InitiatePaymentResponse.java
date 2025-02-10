package com.ekhonni.backend.service.payment.provider.sslcommrez.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Author: Asif Iqbal
 * Date: 1/4/25
 */
@AllArgsConstructor
@Getter
public class InitiatePaymentResponse {
    private String gatewayPageURL;
}
