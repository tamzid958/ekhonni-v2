/**
 * Author: Rifat Shariar Sakil
 * Time: 3:48 PM
 * Date: 1/10/2025
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.validation.annotation;

import com.ekhonni.backend.validation.validator.NonEmptyMultipartFileValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = NonEmptyMultipartFileValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface NonEmptyMultipartFile {
    /**
     * Default error message for validation failure.
     */
    String message() default "Invalid file upload. Please check the file content and format.";

    /**
     * Groups for categorizing constraints.
     */
    Class<?>[] groups() default {};

    /**
     * Payload for carrying metadata information.
     */
    Class<? extends Payload>[] payload() default {};
}
