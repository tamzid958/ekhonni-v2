package com.ekhonni.backend.service.payment.provider.sslcommrez;

import com.ekhonni.backend.service.payment.PaymentProvider;
import com.ekhonni.backend.service.payment.provider.sslcommrez.response.InitiatePaymentResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Author: Asif Iqbal
 * Date: 2/10/25
 */
@Service
@AllArgsConstructor
public class SSLCommerzPaymentProvider implements PaymentProvider  {

    private final SSLCommerzApiClient sslCommerzApiClient;

    @Override
    public InitiatePaymentResponse processPayment(Long bidId) throws Exception {
        return sslCommerzApiClient.initiatePayment(bidId);
    }

    @Override
    public InitiatePaymentResponse processCashIn(Double amount) throws Exception {
        return sslCommerzApiClient.initiateCashIn(amount);
    }
}
