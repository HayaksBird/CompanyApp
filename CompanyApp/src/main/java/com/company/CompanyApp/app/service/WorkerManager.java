package com.company.CompanyApp.app.service;

import com.company.CompanyApp.app.annotations.CorrespondingEntity;
import com.company.CompanyApp.app.annotations.Role;
import com.company.CompanyApp.app.annotations.ViewName;
import com.company.CompanyApp.app.dto.WorkerData;
import com.company.CompanyApp.app.entity.Worker;
import com.company.CompanyApp.app.enums.WorkerType;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 */
@Service
public class WorkerManager {
    /**
     *
     */
    public <T extends Worker> String[] getRoles(Worker worker) throws NoSuchFieldException, ClassNotFoundException, IllegalAccessException {
        String[] roles;
        String fieldName = worker.getWorkerType().name();
        Role rolesAnnotation = WorkerType.class.getField(fieldName).getAnnotation(Role.class);

        if (rolesAnnotation.isTypeBased()) {
            T extWorker = getWorkerExtObject(worker);
            roles = getTypeBasedRoles(extWorker, rolesAnnotation);
        } else roles = rolesAnnotation.roles();

        return roles;
    }


    /**
     *
     */
    public <T extends Worker> T getWorkerExtObject(Worker worker)
                                throws ClassNotFoundException, NoSuchFieldException {

        String fieldName = worker.getWorkerType().name();
        CorrespondingEntity entity = WorkerType.class.getField(fieldName).getAnnotation(CorrespondingEntity.class);

        if (entity == null) {
            throw new IllegalArgumentException("No CorrespondingEntity annotation found for WorkerType: " + fieldName);
        }

        String classPath = String.format("%s.%s", entity.path(), entity.entityClass());

        try {
            //Specify that the class is a subclass of Worker
            Class<T> targetClass = (Class<T>) Class.forName(classPath);

            if (!targetClass.isInstance(worker)) {
                throw new ClassCastException("Cannot cast Worker to " + classPath);
            }

            return targetClass.cast(worker);
        } catch (ClassNotFoundException e) {
            throw new ClassNotFoundException("Class not found: " + classPath, e);
        }
    }


    /**
     * This method extracts all the fields of the worker class that are for the
     * view using reflection.
     */
    public List<WorkerData> getWorkersFields(Object worker) throws IllegalAccessException {
        List<WorkerData> forView = new LinkedList<>();
        Field[] base = worker.getClass().getSuperclass().getDeclaredFields();
        Field[] extended = worker.getClass().getDeclaredFields();

        addFieldsForView(forView, base, worker);
        addFieldsForView(forView, extended, worker);

        return forView;
    }


    /**
     * This method will be used to update the logged user's data. Since their
     * object is represented as a bean in a singleton fashion, all other references
     * throughout the application will notice the changes.
     */
    public <T> void equalize(Class<?> workerClass, T updatable, T roleModel) throws IllegalAccessException {
        if (workerClass.getName().equals("java.lang.Object")) return;
        else equalize(workerClass.getSuperclass(), updatable, roleModel);

        for (Field field : workerClass.getDeclaredFields()) {
            field.setAccessible(true);
            field.set(updatable, field.get(roleModel));
        }
    }


    /**
     * This method focuses on populating the dto list with workers info
     */
    private void addFieldsForView(List<WorkerData> forView,
                                         Field[] fields,
                                         Object worker) throws IllegalAccessException {
        for (Field field : fields) {
            if (field.isAnnotationPresent(ViewName.class)) {
                try {
                    field.setAccessible(true);

                    forView.add(new WorkerData(field.getName(),
                                               field.getAnnotation(ViewName.class).message(),
                                               (field.get(worker) != null ? field.get(worker).toString() : null)));
                } catch (IllegalAccessException ex) {
                    throw new IllegalAccessException("Could not access a field of a worker object " + field.getName());
                }
            }
        }
    }


    /**
     *
     */
    private String[] getTypeBasedRoles(Object extWorker,
                                              Role rolesAnnotation)
                                              throws NoSuchFieldException, IllegalAccessException {

        String type;
        boolean insert = false;
        List<String> roles = new ArrayList<>();
        Field field = extWorker.getClass().getDeclaredField(rolesAnnotation.typeField());

        field.setAccessible(true);
        type = field.get(extWorker).toString();

        for (String role : rolesAnnotation.roles()) {
            if (!role.startsWith("ROLE_") && insert) break;

            if (insert) roles.add(role);

            if (role.equals(type)) insert = true;
        }

        return roles.toArray(new String[0]);
    }
}