package com.ekhonni.backend.config;

import com.ekhonni.backend.interceptor.PaymentLoggingInterceptor;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import javax.net.ssl.SSLContext;
import java.security.cert.X509Certificate;

/**
 * Author: Asif Iqbal
 * Date: 12/28/24
 */

@Configuration
public class RestClientConfig {

    @Bean
    public RestClient paymentRestClient(PaymentLoggingInterceptor interceptor,
                                        SSLCommerzConfig config) {

        return RestClient.builder()
                .requestInterceptor(interceptor)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}

