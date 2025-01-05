package com.ekhonni.backend.config;

import com.ekhonni.backend.interceptor.PaymentLoggingInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

/**
 * Author: Asif Iqbal
 * Date: 12/28/24
 */

@Configuration
public class RestClientConfig {

    @Bean
    public RestClient paymentRestClient(PaymentLoggingInterceptor interceptor) {

        return RestClient.builder()
                .requestInterceptor(interceptor)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}

