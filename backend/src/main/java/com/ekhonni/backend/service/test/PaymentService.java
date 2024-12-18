/**
 * Author: Rifat Shariar Sakil
 * Time: 1:23 AM
 * Date: 12/12/2024
 * Project Name: backend
 */

package com.ekhonni.backend.service.test;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

@Service
public class PaymentService  {

    private static final Logger log = LogManager.getLogger(PaymentService.class);

    @Value("${sslcommerz.api.url}")
    private String sslPaymentGatewayURL;
    @Value("${sslcommerz.api.success-url}")
    private String successUrl;
    @Value("${sslcommerz.api.fail-url}")
    private String failUrl;
    @Value("${sslcommerz.api.cancel-url}")
    private String cancelUrl;
    @Value("${sslcommerz.api.ipn-url}")
    private String ipnUrl;





    private final RestTemplate restTemplate;
    public PaymentService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String makePayment() throws JsonProcessingException {

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("store_id", "dsi67592ee59639d");
        map.add("store_passwd", "dsi67592ee59639d@ssl");
        map.add("total_amount", "100");
        map.add("product_name", "marriage card");
        map.add("currency", "EUR");
        map.add("tran_id", "REF123");
        map.add("success_url", successUrl);
        map.add("fail_url", failUrl);
        map.add("cancel_url", cancelUrl);
        map.add("ipn_url", ipnUrl);
        map.add("cus_name", "Customer Name");
        map.add("cus_email", "cust@yahoo.com");
        map.add("cus_add1", "Dhaka");
        map.add("cus_city", "Dhaka");
        map.add("cus_postcode", "1000");
        map.add("cus_country", "Bangladesh");
        map.add("cus_phone", "01711111111");

        map.add("shipping_method", "NO");
        map.add("multi_card_name", "bkash");
        map.add("value_a", "ref001_A");
        map.add("value_b", "ref002_B");
        map.add("value_c", "ref003_C");
        map.add("value_d", "ref004_D");
        map.add("product_category", "others");
        map.add("product_profile", "general");

        System.out.println(map);


        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/x-www-form-urlencoded");


        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                sslPaymentGatewayURL, HttpMethod.POST, entity, String.class
        );

        //System.out.println(response.getBody());
        String responseBody = response.getBody();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonResponse = objectMapper.readTree(responseBody);
        // Convert to pretty-printed JSON string
        String prettyJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonResponse);

// Print the nicely formatted JSON
        System.out.println(prettyJson);
        //System.out.println(jsonResponse);
        String gatewayPageURL = jsonResponse.path("GatewayPageURL").asText();

        return gatewayPageURL;
    }
}
