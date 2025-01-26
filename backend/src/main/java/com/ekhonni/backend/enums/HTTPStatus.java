/**
 * Author: Rifat Shariar Sakil
 * Time: 12:24 PM
 * Date: 12/9/2024
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum HTTPStatus {

    OK(200, true),
    DELETED(200, true),
    CREATED(201, true),
    ACCEPTED(202, true),
    NO_CONTENT(204, true),
    FOUND(302, true),
    NOT_MODIFIED(304, false),
    BAD_REQUEST(400, false),
    UNAUTHORIZED(401, false),
    PAYMENT_REQUIRED(402, false),
    FORBIDDEN(403, false),
    NOT_FOUND(404, false),

    INTERNAL_SERVER_ERROR(500, false),
    NOT_IMPLEMENTED(501, false),
    BAD_GATEWAY(502, false);

    private final int code;
    private final boolean isSuccess;

}
