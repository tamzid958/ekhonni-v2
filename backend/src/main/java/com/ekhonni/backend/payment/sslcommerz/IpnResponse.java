package com.ekhonni.backend.payment.sslcommerz;

public interface IpnResponse {
    String getTran_id();
    String getVal_id();
    String getAmount();
    String getCard_type();
    String getStore_amount();
    String getCard_no();
    String getBank_tran_id();
    String getStatus();
    String getTran_date();
    String getCurrency();
    String getCard_issuer();
    String getCard_brand();
    String getCard_issuer_country();
    String getCard_issuer_country_code();
    String getStore_id();
    String getVerify_sign();
    String getVerify_key();
    String getCus_fax();
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
