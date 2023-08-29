package com.company.CompanyApp.security.validation.annotations;

import com.company.CompanyApp.security.validation.MatchCodeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = MatchCodeValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MatchCode {
    //
    public String message() default "Wrong code!";

    //
    public Class<?>[] groups() default {};

    //
    public Class<? extends Payload>[] payload() default {};
}
