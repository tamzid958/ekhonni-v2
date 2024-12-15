package com.ekhonni.backend.payment.sslcommerz;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Author: Asif Iqbal
 * Date: 12/15/24
 */

@Component
@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PaymentRequest {
    @Value("${sslcommerz.store.id}")
    private String storeId;
    @Value("${sslcommerz.store.password}")
    private String storePasswd;
    private String totalAmount;
    private String tranId;
    @Value("${payment.success.url}")
    private String successUrl;
    @Value("${payment.fail.url}")
    private String failUrl;
    @Value("${payment.cancel.url}")
    private String cancelUrl;
    @Value("${payment.ipn.url}")
    private String IpnUrl;
    private String cusName;
    private String cusEmail;
    private String cusAdd1;
    private String cusCity;
    private String cusPostcode;
    private String cusCountry;
    private String cusPhone;
    private String shippingMethod;
    private String productName;
    private String productCategory;
    private String productProfile;
}

