package com.ekhonni.backend.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Author: Md Jahid Hasan
 * Date: 2/3/25
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SwaggerCustomDocumentation {
    String mediaType() default "multipart/form-data";

    Class<?> schema() default Void.class;

    String description() default "";

    String[] consumes() default {};

    String[] produces() default {};

    String summary() default "";

    boolean deprecated() default false;
}
