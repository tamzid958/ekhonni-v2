/**
 * Author: Rifat Shariar Sakil
 * Time: 3:48 PM
 * Date: 1/10/2025
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.validation.annotation;

import com.ekhonni.backend.validation.validator.NonEmptyMultipartFileListValidator;
import com.ekhonni.backend.validation.validator.NonEmptyMultipartFileValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {NonEmptyMultipartFileValidator.class, NonEmptyMultipartFileListValidator.class})
public @interface NonEmptyMultipartFile {
    String message() default "All files must be non-empty!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

