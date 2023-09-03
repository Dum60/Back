package com.dumbo.validation;

import com.dumbo.dtos.RegistrationUserDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        RegistrationUserDto user = (RegistrationUserDto) obj;
        return user.getPassword().equals(user.getPasswordConfirm());
    }
}
