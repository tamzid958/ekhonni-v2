package com.ekhonni.backend.service.payment.provider.sslcommrez.request;

import com.ekhonni.backend.config.payment.SSLCommerzConfig;
import lombok.*;

/**
 * Author: Asif Iqbal
 * Date: 12/15/24
 */

@Data
@NoArgsConstructor
public class SSLCommerzPaymentRequest {
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

    public SSLCommerzPaymentRequest(SSLCommerzConfig config, String ipn_url) {
        this.store_id = config.getStoreId();
        this.store_passwd = config.getStorePassword();
        this.success_url = config.getSuccessUrl();
        this.fail_url = config.getFailUrl();
        this.cancel_url = config.getCancelUrl();
        this.ipn_url = ipn_url;
    }

}

