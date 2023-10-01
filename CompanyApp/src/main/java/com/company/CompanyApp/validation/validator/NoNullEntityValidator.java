package com.company.CompanyApp.validation.validator;

import com.company.CompanyApp.app.entity.Worker;
import com.company.CompanyApp.validation.annotations.CanBeNull;
import com.company.CompanyApp.validation.annotations.NoNullEntity;
import com.company.CompanyApp.validation.service.ModelDataService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Make sure that all fields of the worker that are not annotated with
 * 'CanBeNull' are not null.
 */
public class NoNullEntityValidator implements ConstraintValidator<NoNullEntity, Object> {

    private final ModelDataService modelDataService;


    //CONSTRUCTORS
    public NoNullEntityValidator(ModelDataService modelDataService) {
        this.modelDataService = modelDataService;
    }


    @Override
    public void initialize(NoNullEntity constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }


    @Override
    public boolean isValid(Object worker, ConstraintValidatorContext constraintValidatorContext) {
        try {
            return areFieldsValid(worker);
        } catch(Exception ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }


    /**
     * Get the extended version of the worker, and get all of its fields values.
     * Scan through them to make sure that those that are not annotated with 'CanBeNull'
     * are not null.
     */
    private <T extends Worker> boolean areFieldsValid(Object worker)
                                                   throws NoSuchFieldException, IllegalAccessException {

        var workersFields = modelDataService.getModelsFields(worker);

        for (var workerField : workersFields) {
            if (!modelDataService.isAnnotated(worker.getClass(), worker, workerField.getField(), CanBeNull.class)) {
                if (workerField.getValue() == null) return false;
            }
        }

        return true;
    }

}