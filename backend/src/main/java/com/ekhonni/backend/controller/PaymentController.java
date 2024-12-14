package com.ekhonni.backend.controller;

import com.ekhonni.backend.enums.ProductCondition;
import com.ekhonni.backend.model.Product;
import com.ekhonni.backend.model.User;
import com.ekhonni.backend.payment.sslcommerz.Util;
import com.ekhonni.backend.payment.sslcommerz.PaymentRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/api/v2/payment")
public record PaymentController(PaymentRequest paymentRequest) {

    @PostMapping("/initiate")
    public ResponseEntity<?> initiatePayment() {
        try {
            User buyer = new User();
            buyer.setName("John Doe");
            buyer.setEmail("john.doe@example.com");
            buyer.setPassword("securepassword123");
            buyer.setRole("USER");
            buyer.setPhone("123-456-7890");
            buyer.setAddress("123 Main St, Springfield");

            Product product = new Product();
            product.setName("Smartphone");
            product.setPrice(499L);
            product.setDescription("Latest model with high-end features");
            product.setApproved(true);
            product.setSold(false);
            product.setCondition(ProductCondition.NEW);

            String requestBody = Util.getParamsString(paymentRequest, buyer, product, 100000L, true);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED);
            HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

            String sslcommerzApiUrl = "https://sandbox.sslcommerz.com/gwprocess/v4/api.php";
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Map> response = restTemplate.postForEntity(sslcommerzApiUrl, requestEntity, Map.class);

            if (response.getBody() != null && "SUCCESS".equals(response.getBody().get("status"))) {
                System.out.println(response.getBody());
                String gatewayPageURL = (String) response.getBody().get("GatewayPageURL");
                return ResponseEntity.ok(Map.of("gatewayUrl", gatewayPageURL));
            } else {
                String failedReason = response.getBody() != null ? (String) response.getBody().get("failedreason") : "Unknown error";
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", failedReason));
            }
        } catch (UnsupportedEncodingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Error encoding request parameters."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Error initiating payment: " + e.getMessage()));
        }
    }


    @GetMapping("/test")
    public ResponseEntity<?> test() {
        paymentRequest.setCusName("John Doe");
        paymentRequest.setCusEmail("john2@gmail.com");
        return ResponseEntity.ok(paymentRequest);
    }
}
