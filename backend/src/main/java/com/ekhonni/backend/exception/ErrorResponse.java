package com.ekhonni.backend.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    @JsonProperty("status_code")
    private int statusCode;
    private String message;
    private String timestamp;
}
