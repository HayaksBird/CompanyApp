package com.company.CompanyApp.validation.annotations;

import com.company.CompanyApp.validation.validator.PositionTypeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PositionTypeValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PositionType {
    //
    public String message() default "Position type does not exist";

    //
    public String[] types();

    //
    public Class<?>[] groups() default {};

    //
    public Class<? extends Payload>[] payload() default {};
}
