package com.company.CompanyApp.validation.validator;

import com.company.CompanyApp.app.entity.Worker;
import com.company.CompanyApp.app.service.IWorkerService;
import com.company.CompanyApp.validation.annotations.CanBeNull;
import com.company.CompanyApp.validation.annotations.WorkerNoNull;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Make sure that all fields of the worker that are not annotated with
 * 'CanBeNull' are not null.
 */
public class WorkerNoNullValidator implements ConstraintValidator<WorkerNoNull, Worker> {

    IWorkerService workerService;


    //CONSTRUCTORS
    public WorkerNoNullValidator(IWorkerService workerService) {
        this.workerService = workerService;
    }


    @Override
    public void initialize(WorkerNoNull constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }


    @Override
    public boolean isValid(Worker worker, ConstraintValidatorContext constraintValidatorContext) {
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
    private <T extends Worker> boolean areFieldsValid(Worker worker)
                                                   throws NoSuchFieldException, ClassNotFoundException, IllegalAccessException {

        T workerExt = workerService.getWorkerExtObject(worker);
        var workersFields = workerService.getWorkersFields(workerExt);

        for (var workerField : workersFields) {
            if (!workerService.isAnnotated(workerExt.getClass(), workerExt, workerField.getField(), CanBeNull.class)) {
                if (workerField.getValue() == null) return false;
            }
        }

        return true;
    }

}