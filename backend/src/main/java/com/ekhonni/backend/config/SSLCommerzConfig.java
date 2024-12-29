package com.ekhonni.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Author: Asif Iqbal
 * Date: 12/15/24
 */

@Configuration
public class SSLCommerzConfig {
    @Value("${sslcommerz.api.url}")
    public String sslcommerzApiUrl;

    @Bean
    public String getSslcommerzApiUrl() {
        return sslcommerzApiUrl;
    }
}
