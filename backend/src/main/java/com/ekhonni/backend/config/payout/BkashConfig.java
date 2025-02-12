package com.ekhonni.backend.config.payout;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Author: Asif Iqbal
 * Date: 2/3/25
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "bkash")
public class BkashConfig {
    private String username;
    private String password;
    private String appKey;
    private String appSecret;
    private String xAppKey;
    private String merchantInvoiceNumber;
    private String disbursementUrl;
    private String grantTokenUrl;
    private String refreshTokenUrl;
    private String queryBalanceUrl;
    private String intraAccountTransferUrl;
    private String searchTransactionDetailsUrl;
}
