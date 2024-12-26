package com.ekhonni.backend.config;

import lombok.Data;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Asif Iqbal
 * Date: 12/15/24
 */

@Configuration
@Data
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

    private int connectionTimeout = 5000;
    private int readTimeout = 30000;
    private List<String> allowedIps = new ArrayList<>();

}
