package com.ekhonni.backend.payment.sslcommerz;

import com.ekhonni.backend.config.SSLCommerzConfig;
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
public class PaymentRequest {
    private String store_id;
    private String store_passwd;

    private String tran_id;
    private String total_amount;
    private String currency;

    private String success_url;
    private String fail_url;
    private String cancel_url;
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

    public PaymentRequest(SSLCommerzConfig config) {
        this.store_id = config.getStore_id();
        this.store_passwd = config.getStore_passwd();
        this.success_url = config.getSuccess_url();
        this.fail_url = config.getFail_url();
        this.cancel_url = config.getCancel_url();
        this.ipn_url = config.getIpn_url();
    }
}

