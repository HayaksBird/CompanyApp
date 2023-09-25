package com.company.CompanyApp.validation.service;

import com.company.CompanyApp.app.dto.WorkerData;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

import com.company.CompanyApp.exception.TypeParseException;
import com.company.CompanyApp.exception.UnknownTypeException;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class BindingService {
    private final ValidationService validationService;
    private final TypeParserService typeParserService;
    private List<String> errorMessages;


    public BindingService(ValidationService validationService,
                          TypeParserService typeParserService) {

        this.validationService = validationService;
        this.typeParserService = typeParserService;
    }


    public void bindToWorkerEntity(List<WorkerData> workersData,
                                   Object worker) throws IllegalAccessException {

        errorMessages = new LinkedList<>();

        traverse(worker.getClass(), worker, workersData);

        errorMessages.addAll(validationService.validate(worker));
    }


    /**
     * Traverse the object's lineage to make sure that we cover all
     * of it's extended fields.
     */
    private void traverse(Class<?> workerClass, Object worker, List<WorkerData> workersData) throws IllegalAccessException {
        if (workerClass.getName().equals("java.lang.Object")) return;
        else traverse(workerClass.getSuperclass(), worker, workersData);

        Field[] fields = workerClass.getDeclaredFields();
        bind(fields, worker, workersData);
    }


    private void bind(Field[] fields,
                      Object worker,
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
                          Object worker,
                          WorkerData data)
                          throws IllegalAccessException, UnknownTypeException {

        Object dataConverted = null;

        try {
            dataConverted = typeParserService.parse(field.getType(), data.getValue());
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