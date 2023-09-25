package com.company.CompanyApp.validation.service;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Service
public class ValidationService {
    private final Validator validator;


    public ValidationService(@Qualifier("localValidatorFactoryBean")
                             Validator validator) {

        this.validator = validator;
    }


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