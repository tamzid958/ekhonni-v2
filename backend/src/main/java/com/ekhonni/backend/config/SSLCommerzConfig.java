package com.ekhonni.backend.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Author: Asif Iqbal
 * Date: 12/15/24
 */

@Configuration
@Getter
public class SSLCommerzConfig {

    @Value("${sslcommerz.api.url}")
    private String apiUrl;

    @Value("${sslcommerz.store.id}")
    private String store_id;

    @Value("${sslcommerz.store.password}")
    private String store_passwd;

    @Value("${payment.success.url}")
    private String success_url;

    @Value("${payment.fail.url}")
    private String fail_url;

    @Value("${payment.cancel.url}")
    private String cancel_url;

    @Value("${payment.ipn.url}")
    private String ipn_url;

    @Value("${sslcommerz.api.validator.url}")
    private String validatorApiUrl;

}
