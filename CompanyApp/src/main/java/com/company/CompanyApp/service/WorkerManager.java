package com.company.CompanyApp.service;

import com.company.CompanyApp.annotations.CorrespondingEntity;
import com.company.CompanyApp.annotations.Role;
import com.company.CompanyApp.annotations.ViewName;
import com.company.CompanyApp.dto.WorkerData;
import com.company.CompanyApp.entity.worker.Worker;
import com.company.CompanyApp.enums.WorkerType;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 */
public class WorkerManager {
    /**
     *
     */
    public static <T extends Worker> String[] getRoles(Worker worker) throws NoSuchFieldException, ClassNotFoundException, IllegalAccessException {
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
    public static <T extends Worker> T getWorkerExtObject(Worker worker) throws ClassNotFoundException, NoSuchFieldException {
        String fieldName = worker.getWorkerType().name();
        CorrespondingEntity entity = WorkerType.class.getField(fieldName).getAnnotation(CorrespondingEntity.class);

        if (entity == null) {
            throw new IllegalArgumentException("No CorrespondingEntity annotation found for WorkerType: " + fieldName);
        }

        String classPath = String.format("%s.%s", entity.path(), entity.entityClass());

        try {
            //Make sure that the class is a subclass of Worker
            Class<? extends T> targetClass = (Class<? extends T>) Class.forName(classPath);

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
    public static List<WorkerData> getWorkersFields(Object worker) throws IllegalAccessException {
        List<WorkerData> forView = new LinkedList<>();
        Field[] base = worker.getClass().getSuperclass().getDeclaredFields();
        Field[] extended = worker.getClass().getDeclaredFields();

        addFieldsForView(forView, base, worker);
        addFieldsForView(forView, extended, worker);

        return forView;
    }


    /**
     * This method focuses on populating the dto list with workers info
     */
    private static void addFieldsForView(List<WorkerData> forView, Field[] fields, Object worker) throws IllegalAccessException {
        try {
            for (Field field : fields) {
                if (field.isAnnotationPresent(ViewName.class)) {
                    field.setAccessible(true);

                    forView.add(new WorkerData(field.getAnnotation(ViewName.class).message(),
                            field.get(worker)));
                }
            }
        } catch (IllegalAccessException ex) {
            throw new IllegalAccessException("Could not access a field of a worker object");
        }
    }


    /**
     *
     */
    private static String[] getTypeBasedRoles(Object extWorker, Role rolesAnnotation) throws NoSuchFieldException, IllegalAccessException {
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