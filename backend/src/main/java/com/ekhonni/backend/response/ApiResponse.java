/**
 * Author: Rifat Shariar Sakil
 * Time: 11:14 PM
 * Date: 12/8/2024
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.response;


import com.ekhonni.backend.enums.HTTPStatus;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"statusCode", "success", "message", "data"})
public class ApiResponse<T> {
    private int statusCode;
    private boolean isSuccess;
    private T data;
    private String message;

    public ApiResponse(HTTPStatus status, T data) {
        this.statusCode = status.getCode();
        this.data = data;
        this.isSuccess = status.isSuccess();
        this.message = status.isSuccess() ? "successful" : "failed";
    }

}



