package com.spring.boot.server.util.validators;

import com.spring.boot.server.model.Student;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordConstraintValidator implements ConstraintValidator<Password, Student> {

    @Override
    public boolean isValid(Student value, ConstraintValidatorContext context) {
        return !value.getPassword().contains(value.getFirstName()) && !value.getPassword().contains(value.getLastName());
    }

}
