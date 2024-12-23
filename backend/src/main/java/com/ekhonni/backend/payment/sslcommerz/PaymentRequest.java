package com.ekhonni.backend.payment.sslcommerz;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Author: Asif Iqbal
 * Date: 12/15/24
 */

@Component
@Getter
@Setter
@ToString
@NoArgsConstructor
public class PaymentRequest {
    @Value("${sslcommerz.store.id}")
    private String store_id;

    @Value("${sslcommerz.store.password}")
    private String store_passwd;

    private String tran_id;
    private String total_amount;
    private String currency = "BDT";

    @Value("${payment.success.url}")
    private String success_url;

    @Value("${payment.fail.url}")
    private String fail_url;

    @Value("${payment.cancel.url}")
    private String cancel_url;

    @Value("${payment.ipn.url}")
    private String ipn_url;

    private String cus_name;
    private String cus_email;
    private String cus_add1;
    private String cus_city;
    private String cus_postcode;
    private String cus_country;
    private String cus_phone;
    private String shipping_method;
    private String product_name;
    private String product_category;
    private String product_profile;
}

