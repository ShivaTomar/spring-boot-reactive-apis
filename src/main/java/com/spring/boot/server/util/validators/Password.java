package com.spring.boot.server.util.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Constraint(validatedBy = PasswordConstraintValidator.class)
public @interface Password {
    String message() default "Password must not contain first or last name.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
