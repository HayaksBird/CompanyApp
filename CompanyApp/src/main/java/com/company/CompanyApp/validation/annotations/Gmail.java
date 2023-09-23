package com.company.CompanyApp.validation.annotations;

import com.company.CompanyApp.validation.validator.GmailValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = GmailValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Gmail {
    //
    public String message() default "Wrong email format!";

    //
    public Class<?>[] groups() default {};

    //
    public Class<? extends Payload>[] payload() default {};
}
