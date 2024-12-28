package com.ekhonni.backend.payment.sslcommerz;

/**
 * Author: Asif Iqbal
 * Date: 12/28/24
 */
public interface PaymentResponse {
    String getStatus();
    String getTran_id();
    String getVal_id();
    String getAmount();
    String getStore_amount();
    String getCurrency();
    String getBank_tran_id();
    String getTran_date();
    String getCard_type();
    String getCard_no();
    String getCard_issuer();
    String getCard_brand();
    String getCard_issuer_country();
    String getCard_issuer_country_code();
    String getCurrency_type();
    String getCurrency_amount();
    String getCurrency_rate();
    String getBase_fair();
    String getValue_a();
    String getValue_b();
    String getValue_c();
    String getValue_d();
    String getRisk_level();
    String getRisk_title();
}
