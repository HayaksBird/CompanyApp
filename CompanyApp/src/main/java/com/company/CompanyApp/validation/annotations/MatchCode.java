package com.company.CompanyApp.validation.annotations;

import com.company.CompanyApp.validation.validator.MathCodeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Match the code given by the user with the one generated in the authenticationService.
 */
@Constraint(validatedBy = MathCodeValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MatchCode {
    //
    public String message() default "Wrong code";

    //
    public Class<?>[] groups() default {};

    //
    public Class<? extends Payload>[] payload() default {};
}
