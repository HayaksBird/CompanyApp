package com.company.CompanyApp.validation.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation will contain the 'view name' of the field.
 * Meaning, how the field be presented to the user through the GUI
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ViewName {
    public String message() default "Field";
}