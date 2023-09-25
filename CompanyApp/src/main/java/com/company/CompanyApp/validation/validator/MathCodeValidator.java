package com.company.CompanyApp.validation.validator;

import com.company.CompanyApp.security.service.IAuthenticationService;
import com.company.CompanyApp.validation.annotations.MatchCode;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MathCodeValidator implements ConstraintValidator<MatchCode, String> {
    private final IAuthenticationService authenticationService;


    //CONSTRUCTORS
    public MathCodeValidator(IAuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }


    @Override
    public void initialize(MatchCode constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }


    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s != null) return s.equals(authenticationService.getValidationCode());
        else return true;
    }
}
