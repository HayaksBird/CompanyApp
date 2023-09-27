package com.company.CompanyApp.app.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation will specify the role (roles) a worker type
 * has.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface Role {
    /**
    If set to true, a further investigation is needed to determine the
    role of an entity.
     */
    public boolean isPositionTypeBased() default false;


    /**
     * Name of the field in the database that specifies the role of an entity,
     * if it is type based.
     */
    public String typeField() default "positionType";


    /**
     * Takes a single role if the entity is not type based. If it is,
     * then it takes an altering sequence of a type and a corresponding role.
     */
    public String[] roles();
}
