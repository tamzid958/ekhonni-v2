package com.ekhonni.backend.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Author: Md Jahid Hasan
 * Date: 2/4/25
 */
@Getter
@AllArgsConstructor
public enum BlockPolicy {
    TEMPORARY_7_DAYS(7),
    TEMPORARY_30_DAYS(30);

    private final int days;
}

