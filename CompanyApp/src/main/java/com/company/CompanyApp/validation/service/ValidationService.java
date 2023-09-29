package com.company.CompanyApp.validation.service;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Validation service. It focuses on validating the object based on its annotations.
 */
@Service
public class ValidationService {
    private final Validator validator;


    //CONSTRUCTOR
    public ValidationService(@Qualifier("localValidatorFactoryBean")
                             Validator validator) {

        this.validator = validator;
    }


    /**
     * The validation method. Takes an object to be validated as an input and
     * returns a list of error messages. If no error messages to be found, the list
     * will be empty.
     */
    public List<String> validate(Object object) {
        List<String> errorMessages = new LinkedList<>();
        Set<ConstraintViolation<Object>> violations;

        violations = validator.validate(object);

        for (var violation : violations) {
            errorMessages.add(violation.getMessage());
        }

        return errorMessages;
    }
}