package com.ekhonni.backend.util;

import com.ekhonni.backend.enums.HTTPStatus;
import com.ekhonni.backend.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ResponseUtil {

    public static <T> ResponseEntity<ApiResponse<T>> createResponse(HTTPStatus status, T data) {
        return ResponseEntity.status(status.getCode()).body(new ApiResponse<>(status, data));
    }

    public static <T> ResponseEntity<ApiResponse<T>> createResponse(HTTPStatus status) {
        return ResponseEntity.status(status.getCode()).body(new ApiResponse<>(status, null));
    }
}
