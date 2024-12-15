package com.ekhonni.backend.payment.sslcommerz;

import com.ekhonni.backend.model.Product;
import com.ekhonni.backend.model.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
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
public class Util {

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
        String output = "";
        URL url = new URL(stringUrl);
        URLConnection conn = url.openConnection();
        conn.setConnectTimeout(5000);
        conn.setReadTimeout(5000);
        BufferedReader br = new BufferedReader(
                new InputStreamReader(conn.getInputStream()));
        String outputLine;
        while ((outputLine = br.readLine()) != null) {
            output = output + outputLine;
        }
        br.close();
        return output;
    }

    public void constructRequestParameters(PaymentRequest paymentRequest, User buyer, Product product, Long trxId) {
        paymentRequest.setTranId(String.valueOf(trxId));
        paymentRequest.setTotalAmount(String.valueOf(product.getPrice()));

        paymentRequest.setCusName(buyer.getName());
        paymentRequest.setCusEmail(buyer.getEmail());
        paymentRequest.setCusPhone(buyer.getPhone());
        paymentRequest.setCusAdd1(buyer.getAddress());
        paymentRequest.setCusCity("Dhaka");
        paymentRequest.setCusCountry("Bangladesh");

        paymentRequest.setShippingMethod("NO");
        paymentRequest.setProductName(product.getName());
        paymentRequest.setProductCategory("General");
        paymentRequest.setProductProfile("General");
    }

    public String getParamsString(PaymentRequest paymentRequest, User buyer, Product product, Long trxId, boolean urlEncode) throws UnsupportedEncodingException {
        constructRequestParameters(paymentRequest, buyer, product, trxId);
        StringBuilder result = new StringBuilder();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);

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