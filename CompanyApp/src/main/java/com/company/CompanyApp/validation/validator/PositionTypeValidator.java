package com.company.CompanyApp.validation.validator;

import com.company.CompanyApp.validation.annotations.PositionType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PositionTypeValidator implements ConstraintValidator<PositionType, String> {

    String[] types;


    @Override
    public void initialize(PositionType constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        types = constraintAnnotation.types();
    }


    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null) return true;

        for (String type : types) {
            if (type.equals(s.toUpperCase())) return true;
        }

        return false;
    }
}
