package com.company.CompanyApp.validation.validator;

import com.company.CompanyApp.validation.annotations.Numeric;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NumericValidator implements ConstraintValidator<Numeric, String> {

    @Override
    public void initialize(Numeric constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }


    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null) return true;

        return s.matches("\\d+");
    }
}
