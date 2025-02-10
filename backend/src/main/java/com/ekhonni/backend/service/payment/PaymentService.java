package com.ekhonni.backend.service.payment;

import com.ekhonni.backend.dto.cashin.CashInRequest;
import com.ekhonni.backend.dto.payment.PaymentRequest;
import com.ekhonni.backend.enums.PaymentMethod;
import com.ekhonni.backend.factory.payment.PaymentProviderFactory;
import com.ekhonni.backend.service.payment.provider.sslcommrez.response.InitiatePaymentResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author: Asif Iqbal
 * Date: 12/15/24
 */

@Service
@AllArgsConstructor
@Slf4j
public class PaymentService {

    private final List<PaymentProviderFactory> paymentProviderFactories;

    public InitiatePaymentResponse processPayment(PaymentRequest paymentRequest) throws Exception {
        PaymentProviderFactory factory = getPaymentProviderFactory(paymentRequest.paymentMethod());
        PaymentProvider provider = factory.getPaymentProvider();
        return provider.processPayment(paymentRequest.bidId());
    }

    public InitiatePaymentResponse processCashIn(CashInRequest cashInRequest) throws Exception {
        PaymentProviderFactory factory = getPaymentProviderFactory(cashInRequest.paymentMethod());
        PaymentProvider provider = factory.getPaymentProvider();
        return provider.processCashIn(cashInRequest.amount());
    }

    private PaymentProviderFactory getPaymentProviderFactory(PaymentMethod paymentMethod) {
        return paymentProviderFactories.stream()
                .filter(f -> f.getPaymentMethod().equals(paymentMethod))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(String.format("Unsupported payment method: %s", paymentMethod)));
    }

}
