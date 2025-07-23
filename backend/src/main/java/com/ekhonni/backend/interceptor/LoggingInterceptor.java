package com.ekhonni.backend.interceptor;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Set;

@Slf4j
@Component
public class LoggingInterceptor implements ClientHttpRequestInterceptor {

    private static Long requestId = 0L;
    private static final Set<String> SENSITIVE_FIELDS = Set.of(
            "password", "passwd", "apikey", "api_key", "secret", "token", "card_ref_id",
            "store_id", "store_passwd", "card_no", "card_number",
            "cvv", "cvc", "pin", "ssn", "account_number", "bank_account",
            "routing_number", "key", "private_key", "secret_key", "sessionkey",
            "redirectGatewayURL", "GatewayPageURL", "bank_tran_id",
            "verify_sign", "verify_key", "val_id", "ssl_id", "SESSIONKEY"
    );

    @Override
    @NonNull
    public ClientHttpResponse intercept(@NonNull HttpRequest request,
                                        @NonNull byte[] body,
                                        @NonNull ClientHttpRequestExecution execution) throws IOException {
        requestId++;

        try {
            logRequest(requestId, request, body);
            ClientHttpResponse response = execution.execute(request, body);
            byte[] bodyBytes = StreamUtils.copyToByteArray(response.getBody());
            logResponse(requestId, response, bodyBytes);
            return new BufferingClientHttpResponseWrapper(response, bodyBytes);
        } catch (IOException e) {
            log.error("Request {} failed: {}", requestId, e.getMessage());
            throw e;
        }
    }

    private void logRequest(Long requestId, HttpRequest request, byte[] body) {
        try {
            String maskedBody = maskSensitiveData(new String(body, StandardCharsets.UTF_8));
            log.info("============================ Request Begin ===========================");
            log.info("Request ID: {}", requestId);
            log.info("URI: {}", maskUri(request.getURI().toString()));
            log.info("Method: {}", request.getMethod());
            log.info("Headers: {}", maskHeaders(request.getHeaders()));
            log.info("Request Body: {}", maskedBody);
            log.info("============================ Request End =============================");
        } catch (Exception e) {
            log.warn("Failed to log request {}: {}", requestId, e.getMessage());
        }
    }

    private void logResponse(Long requestId, ClientHttpResponse response, byte[] bodyBytes) {
        try {
            String maskedBody = maskSensitiveData(new String(bodyBytes, StandardCharsets.UTF_8));
            log.info("=========================== Response Begin ===========================");
            log.info("Request ID: {}", requestId);
            log.info("Status Code: {}", response.getStatusCode());
            log.info("Status Text: {}", response.getStatusText());
            log.info("Headers: {}", maskHeaders(response.getHeaders()));
            log.info("Response Body: {}", maskedBody);
            log.info("=========================== Response End =============================");
        } catch (Exception e) {
            log.warn("Failed to log response {}: {}", requestId, e.getMessage());
        }
    }

    private String maskSensitiveData(String data) {
        if (data == null || data.isEmpty()) {
            return data;
        }

        // Try parsing as JSON first
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(data);
            return maskJson(jsonNode);
        } catch (Exception e) {
            // If not JSON, try handling as form data
            return maskFormData(data);
        }
    }

    private String maskJson(JsonNode node) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            redactJsonNode(node);
            return mapper.writeValueAsString(node);
        } catch (Exception e) {
            log.warn("Failed to mask JSON data: {}", e.getMessage());
            return "****";
        }
    }

    private void redactJsonNode(JsonNode node) {
        if (node.isObject()) {
            ObjectNode objectNode = (ObjectNode) node;
            objectNode.fields().forEachRemaining(field -> {
                String fieldName = field.getKey().toLowerCase();
                if (isSensitiveField(fieldName)) {
                    objectNode.put(field.getKey(), "****");
                } else if (field.getValue().isObject() || field.getValue().isArray()) {
                    redactJsonNode(field.getValue());
                }
            });
        } else if (node.isArray()) {
            ArrayNode arrayNode = (ArrayNode) node;
            arrayNode.forEach(this::redactJsonNode);
        }
    }

    private String maskFormData(String formData) {
        try {
            String[] pairs = formData.split("&");
            StringBuilder masked = new StringBuilder();
            for (String pair : pairs) {
                if (!masked.isEmpty()) {
                    masked.append("&");
                }
                String[] keyValue = pair.split("=", 2);
                String key = keyValue[0];
                masked.append(key).append("=");
                if (keyValue.length > 1) {
                    masked.append(isSensitiveField(key) ? "****" : keyValue[1]);
                }
            }
            return masked.toString();
        } catch (Exception e) {
            log.warn("Failed to mask form data: {}", e.getMessage());
            return formData;
        }
    }

    private String maskUri(String uri) {
        try {
            String[] parts = uri.split("\\?", 2);
            if (parts.length > 1) {
                return parts[0] + "?" + maskFormData(parts[1]);
            }
            return uri;
        } catch (Exception e) {
            log.warn("Failed to mask URI: {}", e.getMessage());
            return uri;
        }
    }

    private HttpHeaders maskHeaders(HttpHeaders headers) {
        HttpHeaders maskedHeaders = new HttpHeaders();
        headers.forEach((key, value) -> {
            if (isSensitiveField(key)) {
                maskedHeaders.put(key, List.of("****"));
            } else {
                maskedHeaders.put(key, value);
            }
        });
        return maskedHeaders;
    }

    private boolean isSensitiveField(String fieldName) {
        return SENSITIVE_FIELDS.contains(fieldName.toLowerCase());
    }
}



class BufferingClientHttpResponseWrapper implements ClientHttpResponse {
    private final ClientHttpResponse response;
    private final byte[] body;

    BufferingClientHttpResponseWrapper(ClientHttpResponse response, byte[] body) {
        this.response = response;
        this.body = body;
    }

    @Override
    @NonNull
    public java.io.InputStream getBody() {
        return new java.io.ByteArrayInputStream(body);
    }

    @Override
    @NonNull
    public org.springframework.http.HttpHeaders getHeaders() {
        return response.getHeaders();
    }

    @Override
    @NonNull
    public org.springframework.http.HttpStatusCode getStatusCode() throws IOException {
        return response.getStatusCode();
    }

    @Override
    @NonNull
    public String getStatusText() throws IOException {
        return response.getStatusText();
    }

    @Override
    public void close() {
        response.close();
    }
}