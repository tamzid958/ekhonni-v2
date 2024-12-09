/**
 * Author: Rifat Shariar Sakil
 * Time: 12:24 PM
 * Date: 12/9/2024
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum HttpStatusCodes {

    FOUND(200),
    CREATED(201),
    DELETED(200),
    ACCEPTED(202),
    NO_CONTENT(204),
    BAD_REQUEST(400),
    UNAUTHORIZED(401),
    FORBIDDEN(403),
    NOT_FOUND(404),
    INTERNAL_SERVER_ERROR(500);

    private final int code;
    HttpStatusCodes(int code) {
        this.code = code;
    }

    @JsonValue
    public int getCode() {
        return code;
    }
}
