package com.ekhonni.backend.config.payout;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Author: Asif Iqbal
 * Date: 2/3/25
 */
@Configuration
@ConfigurationProperties(prefix = "bkash")
@Data
public class BkashConfig {
    private String username;
    private String password;
    private String appKey;
    private String appSecret;
}
