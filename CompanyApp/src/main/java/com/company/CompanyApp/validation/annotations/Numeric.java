package com.company.CompanyApp.validation.annotations;

import com.company.CompanyApp.validation.validator.NumericValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Check if the string is numeric.
 */
@Constraint(validatedBy = NumericValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Numeric {
    //
    public String message() default "Value must be numeric";

    //
    public Class<?>[] groups() default {};

    //
    public Class<? extends Payload>[] payload() default {};
}
