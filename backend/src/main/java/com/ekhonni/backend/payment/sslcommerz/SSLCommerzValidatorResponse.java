package com.ekhonni.backend.payment.sslcommerz;

public interface SSLCommerzValidatorResponse {
    String getStatus();
    String getTran_date();
    String getTran_id();
    String getVal_id();
    String getAmount();
    String getStore_amount();
    String getCurrency();
    String getBank_tran_id();
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
    String getEmi_instalment();
    String getEmi_amount();
    String getEmi_description();
}
