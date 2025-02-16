package com.ekhonni.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Author: Asif Iqbal
 * Date: 1/7/25
 */

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${cors.allowed-origins}")
    private String[] ALLOWED_ORIGINS;

    @Value("${cors.allowed-methods}")
    private String[] ALLOWED_METHODS;

    @Value("${cors.allowed-headers}")
    private String[] ALLOWED_HEADERS;

    @Value("${cors.exposed-headers}")
    private String[] EXPOSED_HEADERS;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(ALLOWED_ORIGINS)
                .allowedMethods(ALLOWED_METHODS)
                .allowedHeaders(ALLOWED_HEADERS)
                .exposedHeaders(EXPOSED_HEADERS)
                .allowCredentials(true)
                .maxAge(3600);
    }
}