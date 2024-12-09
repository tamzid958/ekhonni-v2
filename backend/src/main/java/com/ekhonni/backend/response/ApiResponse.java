/**
 * Author: Rifat Shariar Sakil
 * Time: 11:14 PM
 * Date: 12/8/2024
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.response;


import com.ekhonni.backend.dto.CategoryDTO;
import com.ekhonni.backend.enums.HttpStatusCodes;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class ApiResponse<T> {
    
    private HttpStatusCodes httpStatusCode;
    private boolean isSuccess;
    private String message;
    private T data;


    public ApiResponse(HttpStatusCodes httpStatusCode, boolean isSuccess, String message, T data) {
        this.httpStatusCode = httpStatusCode;
        this.isSuccess = isSuccess;
        this.message = message;
        this.data = data;
    }

    public static <T> ApiResponse<T> setResponse(HttpStatusCodes httpStatusCode, boolean isSuccess, T data, String message) {
        return new ApiResponse<>(httpStatusCode, isSuccess, message, data);
    }




}
