package com.ekhonni.backend.factory.payment;

import com.ekhonni.backend.enums.PaymentMethod;
import com.ekhonni.backend.service.payment.PaymentProvider;
import com.ekhonni.backend.service.payment.provider.sslcommrez.SSLCommerzPaymentProvider;
import com.ekhonni.backend.service.payment.provider.transfer.TransferPaymentProvider;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Author: Asif Iqbal
 * Date: 2/10/25
 */

@Component
@AllArgsConstructor
public class TransferPaymentProviderFactory implements PaymentProviderFactory {

    private final TransferPaymentProvider transferPaymentProvider;

    @Override
    public PaymentMethod getPaymentMethod() {
        return PaymentMethod.TRANSFER;
    }

    @Override
    public PaymentProvider getPaymentProvider() {
        return transferPaymentProvider;
    }
}
