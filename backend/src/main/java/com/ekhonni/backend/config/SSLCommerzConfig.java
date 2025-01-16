package com.ekhonni.backend.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.Set;

/**
 * Author: Asif Iqbal
 * Date: 12/15/24
 */

@Configuration
@ConfigurationProperties(prefix = "sslcommerz")
@Data
public class SSLCommerzConfig {
    private String apiUrl;
    private String storeId;
    private String storePassword;
    private String successUrl;
    private String failUrl;
    private String cancelUrl;
    private String ipnUrl;
    private String validatorApiUrl;
    private String merchantTransIdValidatorApiUrl;
    private int connectionTimeout = 5000;
    private int readTimeout = 3000;
    private Set<String> allowedIps = new HashSet<>();
}