package com.spring.boot.server.util.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Constraint(validatedBy = AgeConstraintValidator.class)
public @interface Age {
    String message() default "Age cannot be less than 8.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
