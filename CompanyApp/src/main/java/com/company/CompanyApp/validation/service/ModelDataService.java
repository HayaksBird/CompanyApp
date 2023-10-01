package com.company.CompanyApp.validation.service;

import com.company.CompanyApp.validation.annotations.ViewName;
import com.company.CompanyApp.validation.dto.ModelData;
import org.springframework.stereotype.Service;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

/**
 * This is a service class for the app's models. It works with models and prepares
 * them for the GUI view for edition or creation.
 */
@Service
public class ModelDataService {

    /**
     * Check if a certain annotation is present on an object's field.
     */
    public boolean isAnnotated(Class<?> modelClass,
                               Object model,
                               String fieldName,
                               Class<? extends Annotation> annotation) throws NoSuchFieldException {

        try {
            if (!modelClass.getName().equals("java.lang.Object")) {
                Field field = modelClass.getDeclaredField(fieldName);
                field.setAccessible(true);
                return field.isAnnotationPresent(annotation);
            }
        } catch (NoSuchFieldException ex) {
            return isAnnotated(modelClass.getSuperclass(), model, fieldName, annotation);
        }

        throw new NoSuchFieldException(String.format("%s model has no field %s", model.getClass(), fieldName));
    }


    /**
     * Get a list of ModelData from the given object. Note that only the fields
     * annotated with 'ViewName' are taken into the consideration
     */
    public List<ModelData> getModelsFields(Object model) throws IllegalAccessException {
        List<ModelData> forView = new LinkedList<>();

        traverse(model.getClass(), model, forView);

        return forView;
    }


    /**
     * Transfer data from one fields list to another.
     */
    public void mergeModelDataList(List<ModelData> to, List<ModelData> from) {
        for (int i = 0; i < from.size(); i++) {
            if (from.get(i).getField().equals(to.get(i).getField())) {
                to.get(i).setValue(from.get(i).getValue());
            } else break;
        }
    }


    /**
     * Traverse the object's lineage to make sure that we cover all
     * of it's extended fields.
     */
    private void traverse(Class<?> modelClass, Object model, List<ModelData> forView) throws IllegalAccessException {
        if (modelClass.getName().equals("java.lang.Object")) return;
        else traverse(modelClass.getSuperclass(), model, forView);

        Field[] fields = modelClass.getDeclaredFields();
        addFieldsForView(forView, fields, model);
    }


    /**
     * This method focuses on populating the dto list with objects info
     */
    private void addFieldsForView(List<ModelData> forView,
                                  Field[] fields,
                                  Object model) throws IllegalAccessException {

        for (Field field : fields) {
            if (field.isAnnotationPresent(ViewName.class)) {
                try {
                    field.setAccessible(true);

                    forView.add(new ModelData(field.getName(),
                            field.getAnnotation(ViewName.class).message(),
                            (field.get(model) != null ? field.get(model).toString() : null)));
                } catch (IllegalAccessException ex) {
                    throw new IllegalAccessException("Could not access a field of a worker object " + field.getName());
                }
            }
        }
    }
}