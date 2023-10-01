package com.company.CompanyApp.validation.service;

import com.company.CompanyApp.validation.dto.ModelData;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

import com.company.CompanyApp.exception.TypeParseException;
import com.company.CompanyApp.exception.UnknownTypeException;
import org.springframework.stereotype.Service;

/**
 * This service is used to bind data from list of ModelData with its
 * corresponding object.
 */
@Service
public class BindingService {
    private final ValidationService validationService;
    private final TypeParserService typeParserService;
    private List<String> errorMessages;


    //CONSTRUCTORS
    public BindingService(ValidationService validationService,
                          TypeParserService typeParserService) {

        this.validationService = validationService;
        this.typeParserService = typeParserService;
    }


    /**
     * Bind the data form the field's list to its corresponding object.
     */
    public List<String> bindToModelEntity(List<ModelData> fromView,
                                           Object model) throws IllegalAccessException {

        errorMessages = new LinkedList<>();

        traverse(model.getClass(), model, fromView);

        errorMessages.addAll(validationService.validate(model));

        return errorMessages;
    }


    /**
     * Traverse the object's lineage to make sure that we cover all
     * of it's extended fields.
     */
    private void traverse(Class<?> modelClass,
                          Object model,
                          List<ModelData> fromView) throws IllegalAccessException {

        if (modelClass.getName().equals("java.lang.Object")) return;
        else traverse(modelClass.getSuperclass(), model, fromView);

        Field[] fields = modelClass.getDeclaredFields();
        bind(fields, model, fromView);
    }


    /**
     * Match object's field name with the field name from the model.
     * If there is a match, then try to set a new value for this field.
     */
    private void bind(Field[] fields,
                      Object model,
                      List<ModelData> fromView) throws IllegalAccessException {

        for (ModelData workerData : fromView) {
            for (Field baseField : fields) {
                if (baseField.getName().equals(workerData.getField())) {
                    setField(baseField, model, workerData);
                    break;
                }
            }
        }
    }


    /**
     * Try to set the value of the field. If the field is empty, then no attempt
     * is done whatsoever (the null validator will handle it). If the type parser throws
     * an exception, then we set it to the error list for the user.
     */
    private void setField(Field field,
                          Object model,
                          ModelData modelData)
                          throws IllegalAccessException, UnknownTypeException {

        Object dataConverted = null;

        //If no input is passed, then the null validator will handle it
        if (!modelData.getValue().isEmpty()) {
            try {
                dataConverted = typeParserService.parse(field.getType(), modelData.getValue());
            } catch (TypeParseException ex) {
                errorMessages.add(ex.getMessage());
                return;
            }

            field.setAccessible(true);

            field.set(model, dataConverted);
        }
    }
}