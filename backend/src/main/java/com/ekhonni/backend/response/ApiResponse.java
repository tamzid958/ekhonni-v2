/**
 * Author: Rifat Shariar Sakil
 * Time: 11:14 PM
 * Date: 12/8/2024
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.response;


import com.ekhonni.backend.enums.HTTPStatus;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
public class ApiResponse<T> {
    
    private HTTPStatus httpStatusCode;
    private boolean isSuccess;
    private String message;
    private T data;


    public ApiResponse(HTTPStatus httpStatusCode, boolean isSuccess, String message, T data) {
        this.httpStatusCode = httpStatusCode;
        this.isSuccess = isSuccess;
        this.message = message;
        this.data = data;
    }

    public static <T> ApiResponse<T> setResponse(HTTPStatus httpStatusCode, boolean isSuccess, T data, String message) {
        return new ApiResponse<>(httpStatusCode, isSuccess, message, data);
    }




}
