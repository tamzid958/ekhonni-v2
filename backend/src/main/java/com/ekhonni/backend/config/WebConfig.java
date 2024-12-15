/**
 * Author: Rifat Shariar Sakil
 * Time: 6:22 PM
 * Date: 12/11/2024
 * Project Name: backend
 */

package com.ekhonni.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**").allowedOrigins("http://localhost:8081");
    }
}
