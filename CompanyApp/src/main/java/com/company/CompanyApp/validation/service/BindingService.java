package com.company.CompanyApp.validation.service;

import com.company.CompanyApp.app.dto.WorkerData;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

import com.company.CompanyApp.exception.TypeParseException;
import com.company.CompanyApp.exception.UnknownTypeException;
import org.springframework.stereotype.Service;

/**
 * This service is used to bind data from field's list with its
 * corresponding object. Currently, is used only for the Worker amd
 * its extensions.
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


    /**
     * Bind the data form the field's list to its corresponding object.
     */
    public List<String> bindToWorkerEntity(List<WorkerData> workersData,
                                   Object worker) throws IllegalAccessException {

        errorMessages = new LinkedList<>();

        traverse(worker.getClass(), worker, workersData);

        errorMessages.addAll(validationService.validate(worker));

        return errorMessages;
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


    /**
     * Match field object's name with the field name from the workerData.
     * If there is a match, then try to set a new value for this field.
     */
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


    /**
     * Try to set the value of the field. If the field is empty, then no attempt
     * is done whatsoever (the null validator will handle it). If the type parser throws an exception,
     * then we set it to the error list for the user.
     */
    private void setField(Field field,
                          Object worker,
                          WorkerData data)
                          throws IllegalAccessException, UnknownTypeException {

        Object dataConverted = null;

        //If no input is passed, then the null validator will handle it
        if (!data.getValue().isEmpty()) {
            try {
                dataConverted = typeParserService.parse(field.getType(), data.getValue());
            } catch (TypeParseException ex) {
                errorMessages.add(ex.getMessage());
                return;
            }

            field.setAccessible(true);

            field.set(worker, dataConverted);
        }
    }
}