package com.company.CompanyApp.validation.service;

import com.company.CompanyApp.app.dto.WorkerData;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.company.CompanyApp.exception.TypeParseException;
import com.company.CompanyApp.exception.UnknownTypeException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class BindingService <T> {
    private final Validator validator;
    private List<String> errorMessages;


    public BindingService(@Qualifier("localValidatorFactoryBean")
                          Validator validator) {

        this.validator = validator;
    }


    public void bindToWorkerEntity(List<WorkerData> workersData,
                                   T worker) throws IllegalAccessException {

        errorMessages = new LinkedList<>();
        Set<ConstraintViolation<T>> violations;

        traverse((Class<T>) worker.getClass(), worker, workersData);

        violations = validator.validate(worker);

        for (var violation : violations) {
            errorMessages.add(violation.getMessage());
        }
    }


    /**
     * Traverse the object's lineage to make sure that we cover all
     * of it's extended fields.
     */
    private void traverse(Class<T> workerClass, T worker, List<WorkerData> workersData) throws IllegalAccessException {
        if (workerClass.getName().equals("java.lang.Object")) return;
        else traverse((Class<T>) workerClass.getSuperclass(), worker, workersData);

        Field[] fields = workerClass.getDeclaredFields();
        bind(fields, worker, workersData);
    }


    private void bind(Field[] fields,
                      T worker,
                      List<WorkerData> workersData) throws IllegalAccessException {

        for (WorkerData workerData : workersData) {
            for (Field baseField : fields) {
                if (baseField.getName().equals(workerData.getField())) {
                    setField(baseField, worker, workerData);
                    break;
                }
            }
        }
    }


    private void setField(Field field,
                          T worker,
                          WorkerData data)
                          throws IllegalAccessException, UnknownTypeException {

        Object dataConverted = null;

        try {
            dataConverted = TypeParserService.parse(field.getType(), data.getValue());
        } catch (TypeParseException ex) {
            errorMessages.add(ex.getMessage());
            return;
        }

        field.setAccessible(true);

        field.set(worker, dataConverted);
    }


    //Setters & Getters
    public List<String> getErrorMessages() {
        return errorMessages;
    }
}