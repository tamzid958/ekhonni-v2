/**
 * Author: Rifat Shariar Sakil
 * Time: 1:32 AM
 * Date: 12/12/2024
 * Project Name: backend
 */

package com.ekhonni.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
