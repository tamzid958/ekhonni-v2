package com.ekhonni.backend.service.payment;

import com.ekhonni.backend.service.payment.provider.sslcommrez.response.InitiatePaymentResponse;

/**
 * Author: Asif Iqbal
 * Date: 2/10/25
 */

public interface PaymentProvider {
    InitiatePaymentResponse processPayment(Long bidId) throws Exception;
    InitiatePaymentResponse processCashIn(Double amount) throws Exception;
}
