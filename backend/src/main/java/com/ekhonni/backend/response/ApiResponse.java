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
    
    private HTTPStatus StatusCode;
    private boolean isSuccess;
    private String message;
    private T data;


    public ApiResponse(HTTPStatus StatusCode, boolean isSuccess, String message, T data) {
        this.StatusCode = StatusCode;
        this.isSuccess = isSuccess;
        this.message = message;
        this.data = data;
    }

    public static <T> ApiResponse<T> setResponse(HTTPStatus statusCode, boolean isSuccess, T data, String message) {
        return new ApiResponse<>(statusCode, isSuccess, message, data);
    }


}
