package com.ekhonni.backend.factory.payment;

import com.ekhonni.backend.enums.PaymentMethod;
import com.ekhonni.backend.service.payment.PaymentProvider;
import com.ekhonni.backend.service.payment.provider.sslcommrez.SSLCommerzPaymentProvider;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Author: Asif Iqbal
 * Date: 2/10/25
 */

@Component
@AllArgsConstructor
public class SSLCommerzPaymentProviderFactory implements PaymentProviderFactory {

    private final SSLCommerzPaymentProvider sslCommerzPaymentProvider;

    @Override
    public PaymentMethod getPaymentMethod() {
        return PaymentMethod.SSLCOMMERZ;
    }

    @Override
    public PaymentProvider getPaymentProvider() {
        return sslCommerzPaymentProvider;
    }
}
