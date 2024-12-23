package com.ekhonni.backend.payment.sslcommerz;

import com.ekhonni.backend.model.Product;
import com.ekhonni.backend.model.Transaction;
import com.ekhonni.backend.model.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Component
@AllArgsConstructor
public class Util {

    private final PaymentRequest paymentRequest;

    public SSLCommerzInitResponse extractInitResponse(String response) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper.readValue(response, SSLCommerzInitResponse.class);
    }

    public SSLCommerzValidatorResponse extractValidatorResponse(String response) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper.readValue(response, SSLCommerzValidatorResponse.class);
    }

    public String getByOpeningJavaUrlConnection(String stringUrl) throws IOException {
        StringBuilder output = new StringBuilder();
        URL url = new URL(stringUrl);
        URLConnection conn = url.openConnection();
        conn.setConnectTimeout(5000);
        conn.setReadTimeout(5000);
        BufferedReader br = new BufferedReader(
                new InputStreamReader(conn.getInputStream()));
        String outputLine;
        while ((outputLine = br.readLine()) != null) {
            output.append(outputLine);
        }
        br.close();
        return output.toString();
    }

    public void constructRequestParameters(Transaction transaction) {
        Product product = transaction.getProduct();
        User buyer = transaction.getBuyer();
        paymentRequest.setTran_id(String.valueOf(transaction.getId()));
        paymentRequest.setTotal_amount(String.valueOf(product.getPrice()));

        paymentRequest.setCus_name(buyer.getName());
        paymentRequest.setCus_email(buyer.getEmail());
        paymentRequest.setCus_phone(buyer.getPhone());
        paymentRequest.setCus_add1(buyer.getAddress());
        paymentRequest.setCus_city("Dhaka");
        paymentRequest.setCus_country("Bangladesh");

        paymentRequest.setShipping_method("NO");
        paymentRequest.setProduct_name(product.getName());
        paymentRequest.setProduct_category("General");
        paymentRequest.setProduct_profile("General");
    }

    public String getParamsString(Transaction transaction, boolean urlEncode) throws UnsupportedEncodingException {
        constructRequestParameters(transaction);
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