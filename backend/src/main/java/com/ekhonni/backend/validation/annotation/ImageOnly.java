/**
 * Author: Rifat Shariar Sakil
 * Time: 7:07 PM
 * Date: 1/10/2025
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.validation.annotation;

import com.ekhonni.backend.validation.validator.ImageValidator;
import com.ekhonni.backend.validation.validator.ImageValidatorList;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = {ImageValidator.class, ImageValidatorList.class})
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ImageOnly {
    String message() default "Only images are allowed";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
