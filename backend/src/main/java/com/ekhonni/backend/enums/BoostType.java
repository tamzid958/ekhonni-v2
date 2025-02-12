/**
 * Author: Rifat Shariar Sakil
 * Time: 2:13 PM
 * Date: 2/12/25
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.enums;



import lombok.Getter;


import java.time.temporal.ChronoUnit;


@Getter
public enum BoostType {
    ONE_WEEK(100.0, 1, ChronoUnit.WEEKS),
    TWO_WEEKS(180.0, 2, ChronoUnit.WEEKS),
    ONE_MONTH(350.0, 1, ChronoUnit.MONTHS);

    private final double amount;
    private final int duration;
    private final ChronoUnit unit;

    BoostType(double amount, int duration, ChronoUnit unit) {
        this.amount = amount;
        this.duration = duration;
        this.unit = unit;
    }

}