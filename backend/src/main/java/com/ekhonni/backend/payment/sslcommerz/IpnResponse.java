package com.ekhonni.backend.payment.sslcommerz;

public interface IpnResponse extends PaymentResponse {
    String getStore_id();
    String getVerify_sign();
    String getVerify_key();
    String getCus_fax();
}
