package com.company.CompanyApp.validation.validator;

import com.company.CompanyApp.validation.annotations.Gmail;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class GmailValidator implements ConstraintValidator<Gmail, String> {
    @Override
    public void initialize(Gmail constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }


    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null) return true;

        return s.endsWith("@gmail.com");
    }
}
