package com.ekhonni.backend.interceptor;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@Slf4j
@Component
public class LoggingInterceptor implements ClientHttpRequestInterceptor {

    private static Long requestId = 0L;

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
            log.info("============================ Request Begin ===========================");
            log.info("Request ID: {}", requestId);
            log.info("URI: {}", request.getURI());
            log.info("Method: {}", request.getMethod());
            log.info("Headers: {}", request.getHeaders());
            log.info("Request Body: {}", redactSensitiveData(new String(body, StandardCharsets.UTF_8)));
            log.info("============================ Request End =============================");
        } catch (Exception e) {
            log.warn("Failed to log request {}: {}", requestId, e.getMessage());
        }
    }

    private void logResponse(Long requestId, ClientHttpResponse response, byte[] bodyBytes) {
        try {
            log.info("=========================== Response Begin ===========================");
            log.info("Request ID: {}", requestId);
            log.info("Status Code: {}", response.getStatusCode());
            log.info("Status Text: {}", response.getStatusText());
            log.info("Headers: {}", response.getHeaders());
            log.info("Response Body: {}", redactSensitiveData(new String(bodyBytes, StandardCharsets.UTF_8)));
            log.info("=========================== Response End =============================");
        } catch (Exception e) {
            log.warn("Failed to log response {}: {}", requestId, e.getMessage());
        }
    }

    private String redactSensitiveData(String data) {
        if (data == null || data.isEmpty()) {
            return data;
        }

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(data);
            redactJsonNode(jsonNode);
            return mapper.writeValueAsString(jsonNode);
        } catch (Exception e) {
            log.warn("Failed to redact sensitive data: {}", e.getMessage());
            return data;
        }
    }

    private void redactJsonNode(JsonNode node) {
        if (node.isObject()) {
            ObjectNode objectNode = (ObjectNode) node;
            Iterator<Map.Entry<String, JsonNode>> fields = objectNode.fields();
            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> field = fields.next();
                String fieldName = field.getKey().toLowerCase();
                if (isSensitiveField(fieldName)) {
                    objectNode.put(field.getKey(), "****");
                } else if (field.getValue().isObject() || field.getValue().isArray()) {
                    redactJsonNode(field.getValue());
                }
            }
        } else if (node.isArray()) {
            ArrayNode arrayNode = (ArrayNode) node;
            for (JsonNode element : arrayNode) {
                redactJsonNode(element);
            }
        }
    }

    private boolean isSensitiveField(String fieldName) {
        Set<String> sensitiveFields = Set.of(
                "password",
                "apikey",
                "store_id",
                "store_passwd"
        );
        return sensitiveFields.contains(fieldName.toLowerCase());
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