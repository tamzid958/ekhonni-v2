package com.ekhonni.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StringConfig {

    @Bean
    public String stringBean() {
        return "";
    }
}
