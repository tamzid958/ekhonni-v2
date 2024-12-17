package com.ekhonni.backend.service;

import com.ekhonni.backend.exception.ProductNotFoundException;
import com.ekhonni.backend.exception.UserNotFoundException;
import com.ekhonni.backend.model.Product;
import com.ekhonni.backend.model.User;
import com.ekhonni.backend.payment.sslcommerz.PaymentRequest;
import com.ekhonni.backend.payment.sslcommerz.SSLCommerzInitResponse;
import com.ekhonni.backend.payment.sslcommerz.Util;
import com.ekhonni.backend.projection.GatewayResponseProjection;
import com.ekhonni.backend.repository.ProductRepository;
import com.ekhonni.backend.repository.UserRepository;
import com.ekhonni.backend.response.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

/**
 * Author: Asif Iqbal
 * Date: 12/15/24
 */
@Service
@AllArgsConstructor
@Slf4j
public class PaymentService {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final PaymentRequest paymentRequest;
    private final ProjectionFactory projectionFactory;
    private final Util util;
    private final String sslcommerzApiUrl;

    public ApiResponse<?> initiatePayment(UUID buyerId, Long productId) {
        try {
            User buyer = userRepository.findById(buyerId)
                    .orElseThrow(UserNotFoundException::new);

            Product product = productRepository.findById(productId)
                    .orElseThrow(ProductNotFoundException::new);

            String requestBody = util.getParamsString(paymentRequest, buyer, product, 100000L, true);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED);
            HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

            RestTemplate restTemplate = new RestTemplate();
            SSLCommerzInitResponse response = restTemplate.postForEntity(sslcommerzApiUrl, requestEntity, SSLCommerzInitResponse.class).getBody();
            log.info("Response: {}", response);

            if (response != null && "SUCCESS".equals(response.getStatus())) {
                GatewayResponseProjection responseProjection = projectionFactory.createProjection(GatewayResponseProjection.class, response);
                return new ApiResponse<>(true, "Success", responseProjection, HttpStatus.OK);
            } else {
                String message = "Unknown error";
                if (response != null) {
                    message = response.getFailedReason();
                }
                return new ApiResponse<>(false, message, null, HttpStatus.BAD_REQUEST);
            }
        } catch (UnsupportedEncodingException e) {
            return new ApiResponse<>(false, "Unsupported encoding", null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ApiResponse<>(false, "Error", null, HttpStatus.BAD_REQUEST);
        }
    }
}
