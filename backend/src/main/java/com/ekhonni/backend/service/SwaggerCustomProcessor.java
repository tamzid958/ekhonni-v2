package com.ekhonni.backend.service;

import com.ekhonni.backend.annotation.SwaggerCustomDocumentation;
import org.springframework.stereotype.Component;

/**
 * Author: Md Jahid Hasan
 * Date: 2/3/25
 */
@Component
public class SwaggerCustomProcessor {
    private final ThreadLocal<SwaggerCustomDocumentation> currentAnnotation = new ThreadLocal<>();

    public void setCurrentAnnotation(SwaggerCustomDocumentation annotation) {
        currentAnnotation.set(annotation);
    }

    public String getMediaType() {
        SwaggerCustomDocumentation annotation = currentAnnotation.get();
        return annotation != null ? annotation.mediaType() : "multipart/form-data";
    }

    public Class<?> getSchema() {
        SwaggerCustomDocumentation annotation = currentAnnotation.get();
        return annotation != null ? annotation.schema() : Void.class;
    }

    public String getDescription() {
        SwaggerCustomDocumentation annotation = currentAnnotation.get();
        return annotation != null ? annotation.description() : "";
    }

    public String[] getConsumes() {
        SwaggerCustomDocumentation annotation = currentAnnotation.get();
        return annotation != null ? annotation.consumes() : new String[]{};
    }

    public String[] getProduces() {
        SwaggerCustomDocumentation annotation = currentAnnotation.get();
        return annotation != null ? annotation.produces() : new String[]{};
    }

    public String getSummary() {
        SwaggerCustomDocumentation annotation = currentAnnotation.get();
        return annotation != null ? annotation.summary() : "";
    }

    public boolean isDeprecated() {
        SwaggerCustomDocumentation annotation = currentAnnotation.get();
        return annotation != null && annotation.deprecated();
    }

    public void clearCurrentAnnotation() {
        currentAnnotation.remove();
    }
}

