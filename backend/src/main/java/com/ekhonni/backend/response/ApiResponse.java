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
@AllArgsConstructor


public class ApiResponse<T> {
    private HttpStatusCodes httpStatusCode;
    private boolean success;
    private String message;
    private T data;


    public static <T> ApiResponse<T> setResponse(HttpStatusCodes httpStatusCode, boolean success, T data, String message) {
        return new ApiResponse<>(httpStatusCode, success, message, data);
    }


}
