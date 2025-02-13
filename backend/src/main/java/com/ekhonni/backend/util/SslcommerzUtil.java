package com.ekhonni.backend.util;

import com.ekhonni.backend.config.payment.SSLCommerzConfig;
import com.ekhonni.backend.model.CashIn;
import com.ekhonni.backend.model.Transaction;
import com.ekhonni.backend.model.User;
import com.ekhonni.backend.service.payment.provider.sslcommrez.request.SSLCommerzPaymentRequest;
import com.ekhonni.backend.service.payment.provider.sslcommrez.response.InitialResponse;
import com.ekhonni.backend.service.payment.provider.sslcommrez.response.IpnResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Component
@AllArgsConstructor
@Slf4j
public class SslcommerzUtil {

    private final SSLCommerzConfig sslCommerzConfig;

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

    private SSLCommerzPaymentRequest constructPaymentRequestParameters(Transaction transaction) {
        SSLCommerzPaymentRequest paymentRequest = new SSLCommerzPaymentRequest(
                sslCommerzConfig, sslCommerzConfig.getPaymentIpnUrl());

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
        paymentRequest.setProduct_name(transaction.getProduct().getTitle());
        paymentRequest.setProduct_category("General");
        paymentRequest.setProduct_profile("General");
        return paymentRequest;
    }

    private SSLCommerzPaymentRequest constructCashInRequestParameters(CashIn cashIn) {
        SSLCommerzPaymentRequest cashInRequest = new SSLCommerzPaymentRequest(
                sslCommerzConfig, sslCommerzConfig.getCashInIpnUrl());

        User user = cashIn.getAccount().getUser();
        cashInRequest.setTran_id(String.valueOf(cashIn.getId()));
        cashInRequest.setTotal_amount(String.valueOf(cashIn.getAmount()));
        cashInRequest.setCurrency(cashIn.getCurrency());

        cashInRequest.setCus_name(user.getName());
        cashInRequest.setCus_email(user.getEmail());
        cashInRequest.setCus_phone(user.getPhone());
        cashInRequest.setCus_add1(user.getAddress());
        cashInRequest.setCus_city("Dhaka");
        cashInRequest.setCus_country("Bangladesh");

        cashInRequest.setShipping_method("NO");
        cashInRequest.setProduct_name("cashIn");
        cashInRequest.setProduct_category("General");
        cashInRequest.setProduct_profile("General");
        return cashInRequest;
    }

    public String getParamsString(Transaction transaction, boolean urlEncode) {
        SSLCommerzPaymentRequest sslCommerzPaymentRequest = constructPaymentRequestParameters(transaction);
        Map<String, Object> fieldMap = convertToMap(sslCommerzPaymentRequest);
        return buildParameterString(fieldMap, urlEncode);
    }

    public String getParamsString(CashIn cashIn, boolean urlEncode) {
        SSLCommerzPaymentRequest cashInRequest = constructCashInRequestParameters(cashIn);
        Map<String, Object> fieldMap = convertToMap(cashInRequest);
        return buildParameterString(fieldMap, urlEncode);
    }

    private Map<String, Object> convertToMap(SSLCommerzPaymentRequest request) {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(request, new TypeReference<>() {});
    }

    private String buildParameterString(Map<String, Object> fieldMap, boolean urlEncode) {
        StringBuilder result = new StringBuilder();

        for (Map.Entry<String, Object> entry : fieldMap.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if (value != null) {
                if (!result.isEmpty()) {
                    result.append("&");
                }
                if (urlEncode) {
                    result.append(URLEncoder.encode(key, StandardCharsets.UTF_8))
                            .append("=")
                            .append(URLEncoder.encode(value.toString(), StandardCharsets.UTF_8));
                } else {
                    result.append(key)
                            .append("=")
                            .append(value);
                }
            }
        }
        return result.toString();
    }
}