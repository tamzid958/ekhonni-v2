package com.ekhonni.backend.payment.sslcommerz;

/**
 * Author: Asif Iqbal
 * Date: 12/26/24
 */
public interface ValidationResponse extends PaymentResponse {
    String getEmi_instalment();
    String getEmi_amount();
    String getEmi_description();
    String getEmi_issuer();
    String getAccount_details();
    String getAPIConnect();
    String getValidated_on();
    String getGw_version();
}
