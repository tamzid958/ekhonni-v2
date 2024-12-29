package com.ekhonni.backend.config;

import com.ekhonni.backend.interceptor.PaymentLoggingInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

/**
 * Author: Asif Iqbal
 * Date: 12/28/24
 */
@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate(SSLCommerzConfig config) {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(config.getConnectionTimeout());
        factory.setReadTimeout(config.getReadTimeout());

        RestTemplate template = new RestTemplate(factory);
        template.setInterceptors(Collections.singletonList(new PaymentLoggingInterceptor()));
        return template;
    }
}
