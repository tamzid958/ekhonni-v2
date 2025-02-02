package com.ekhonni.backend.validation.annotation;
import com.ekhonni.backend.validation.validator.PayoutAccountValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Author: Asif Iqbal
 * Date: 2/2/25
 */

@Constraint(validatedBy = PayoutAccountValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPayoutAccount {
    String message() default "Invalid payout account data";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
