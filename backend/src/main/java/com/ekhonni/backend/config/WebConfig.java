package com.ekhonni.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Author: Asif Iqbal
 * Date: 1/7/25
 */

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(
                        "http://localhost:3000",
                        "http://127.0.0.1:3000",
                        "http://host.docker.internal:3000"
                )
                .allowedMethods(
                        "GET",
                        "POST",
                        "PUT",
                        "DELETE",
                        "PATCH",
                        "OPTIONS"
                )
                .allowedHeaders(
                        "Authorization",
                        "Content-Type",
                        "Accept",
                        "Origin",
                        "Access-Control-Request-Method",
                        "Access-Control-Request-Headers"
                )
                .exposedHeaders(
                        "Access-Control-Allow-Origin",
                        "Access-Control-Allow-Credentials"
                )
                .allowCredentials(true)
                .maxAge(3600);

        registry.addMapping("/api/v2/payment/ipn")
                .allowedOriginPatterns("developer.sslcommerz.com")
                .allowedMethods("GET", "POST")
                .allowedHeaders("Authorization", "Content-Type")
                .exposedHeaders("Access-Control-Allow-Origin")
                .allowCredentials(false)
                .maxAge(1800);
    }
}
