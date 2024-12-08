/**
 * Author: Rifat Shariar Sakil
 * Time: 11:14 PM
 * Date: 12/8/2024
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Setter
@Getter
@AllArgsConstructor
@Builder


public class ApiResponse<T>{
    private HttpStatus httpStatus;
    private boolean success;
    private String message;
    private T data;

    public ApiResponse(boolean success, String message, T data, HttpStatus httpStatus) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.httpStatus = httpStatus;
    }

}
