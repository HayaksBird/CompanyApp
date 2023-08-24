package com.company.CompanyApp.security.validation;

import com.company.CompanyApp.security.dto.VerificationRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MatchCodeValidator implements ConstraintValidator<MatchCode, String> {
    @Override
    public void initialize(MatchCode constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s != null) return s.equals(VerificationRequest.getCode());
        else return true;
    }
}
