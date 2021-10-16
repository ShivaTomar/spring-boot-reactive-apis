package com.spring.boot.server.util.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AgeConstraintValidator implements ConstraintValidator<Age, Integer> {

    @Override
    public boolean isValid(Integer age, ConstraintValidatorContext context) {
        return age >= 8;
    }

}
