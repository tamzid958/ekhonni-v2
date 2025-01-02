package com.ekhonni.backend.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

/**
 * Author: Md Jahid Hasan
 * Date: 1/1/25
 */
@Component
public class JsonUtil {

    private final ObjectMapper objectMapper;

    public JsonUtil(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public <T> List<T> readListFromJsonFile(String filePath, TypeReference<List<T>> typeReference) throws IOException {
        ClassPathResource resource = new ClassPathResource(filePath);
        String jsonContent = new String(Files.readAllBytes(resource.getFile().toPath()));
        return objectMapper.readValue(jsonContent, typeReference);
    }
}
