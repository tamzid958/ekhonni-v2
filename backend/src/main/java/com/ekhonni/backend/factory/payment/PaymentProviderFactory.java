package com.ekhonni.backend.factory.payment;

import com.ekhonni.backend.enums.PaymentMethod;
import com.ekhonni.backend.service.payment.PaymentProvider;

/**
 * Author: Asif Iqbal
 * Date: 2/10/25
 */
public interface PaymentProviderFactory {
    PaymentMethod getPaymentMethod();
    PaymentProvider getPaymentProvider();
}
