package com.ekhonni.backend.payment.sslcommerz;

import com.ekhonni.backend.model.Transaction;
import com.ekhonni.backend.model.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Component
@AllArgsConstructor
@Slf4j
public class Util {

    private final PaymentRequest paymentRequest;

    public InitialResponse extractInitResponse(Map<String, String> response) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper.convertValue(response, InitialResponse.class);
    }

    public IpnResponse extractIpnResponse(Map<String, String> response) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper.convertValue(response, IpnResponse.class);
    }

    public void constructRequestParameters(Transaction transaction) {
        User buyer = transaction.getBuyer();
        paymentRequest.setTran_id(String.valueOf(transaction.getId()));
        paymentRequest.setTotal_amount(String.valueOf(transaction.getAmount()));
        paymentRequest.setCurrency(transaction.getCurrency());

        paymentRequest.setCus_name(buyer.getName());
        paymentRequest.setCus_email(buyer.getEmail());
        paymentRequest.setCus_phone(buyer.getPhone());
        paymentRequest.setCus_add1(buyer.getAddress());
        paymentRequest.setCus_city("Dhaka");
        paymentRequest.setCus_country("Bangladesh");

        paymentRequest.setShipping_method("NO");
        paymentRequest.setProduct_name(transaction.getProduct().getName());
        paymentRequest.setProduct_category("General");
        paymentRequest.setProduct_profile("General");
    }

    public String getParamsString(Transaction transaction, boolean urlEncode) throws UnsupportedEncodingException {
        constructRequestParameters(transaction);
        log.info("Payment request parameters: {}", paymentRequest.toString());
        StringBuilder result = new StringBuilder();
        ObjectMapper objectMapper = new ObjectMapper();

        Map<String, Object> fieldMap = objectMapper.convertValue(paymentRequest, new TypeReference<>() {});
        for (Map.Entry<String, Object> entry : fieldMap.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if (value != null) {
                if (urlEncode) {
                    result.append(URLEncoder.encode(key, StandardCharsets.UTF_8));
                    result.append("=");
                    result.append(URLEncoder.encode(value.toString(), StandardCharsets.UTF_8));
                } else {
                    result.append(key);
                    result.append("=");
                    result.append(value);
                }
                result.append("&");
            }
        }

        String resultString = result.toString();
        return !resultString.isEmpty() ? resultString.substring(0, resultString.length() - 1) : resultString;
    }
}