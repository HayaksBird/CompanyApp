package com.company.CompanyApp.validation.annotations;

import com.company.CompanyApp.validation.validator.WorkerNoNullValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = WorkerNoNullValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface WorkerNoNull {
    //
    public String message() default "Fill in the necessary fields";


    //
    public Class<?>[] groups() default {};

    //
    public Class<? extends Payload>[] payload() default {};
}
