package com.ekhonni.backend.config;

import com.ekhonni.backend.interceptor.PaymentLoggingInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

/**
 * Author: Asif Iqbal
 * Date: 12/28/24
 */
@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate paymentRestTemplate(PaymentLoggingInterceptor interceptor) {
        RestTemplate template = new RestTemplate();
        template.setInterceptors(Collections.singletonList(interceptor));
        return template;
    }
}
