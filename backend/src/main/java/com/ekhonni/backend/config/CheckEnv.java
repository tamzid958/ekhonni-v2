package com.ekhonni.backend.config;

import com.ekhonni.backend.config.payout.BkashConfig;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class CheckEnv {

    private final SSLCommerzConfig sslCommerzConfig;

    private final BkashConfig bkashConfig;


    @PostConstruct
    public void checkEnvironmentVariables() {
        log.info("Checking bKash configuration variables:");
        log.info("----------------------------------------");

        checkVariable("bkash.username", bkashConfig.getUsername());
        checkVariable("bkash.password", bkashConfig.getPassword());
        checkVariable("bkash.app-key", bkashConfig.getAppKey());
        checkVariable("bkash.app-secret", bkashConfig.getAppSecret());
        checkVariable("sslcommerz.domain", sslCommerzConfig.getDomain());
        checkVariable("sslcommerz.success-url", sslCommerzConfig.getSuccessUrl());


        log.info("----------------------------------------");
    }

    private void checkVariable(String name, String value) {
        if (value == null || value.isEmpty()) {
            log.error("{} is not set!", name);
        } else {
            log.info("{} is set with length: {}", name, value.length());
            // For debugging only, be careful with sensitive data
            // log.debug("{}: {}", name, value);
        }
    }
}