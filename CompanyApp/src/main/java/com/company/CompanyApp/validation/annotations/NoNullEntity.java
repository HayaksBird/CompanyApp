package com.company.CompanyApp.validation.annotations;

import com.company.CompanyApp.validation.validator.NoNullEntityValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotate an entity, whose fields you do not wish to be null.
 * Works in pair with the 'CanBeNull', which when placed on an
 * individual field allows it to be null.
 */
@Constraint(validatedBy = NoNullEntityValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface NoNullEntity {
    //
    public String message() default "Fill in the necessary fields";


    //
    public Class<?>[] groups() default {};

    //
    public Class<? extends Payload>[] payload() default {};
}
