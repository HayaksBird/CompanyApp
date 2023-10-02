package com.company.CompanyApp.app.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface CorrespondingEntity {
    public String entityClass();

    public String path() default "com.company.CompanyApp.app.entity.worker";
}
