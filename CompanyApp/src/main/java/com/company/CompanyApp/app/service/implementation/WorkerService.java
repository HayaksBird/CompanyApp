package com.company.CompanyApp.app.service.implementation;

import com.company.CompanyApp.app.annotations.CorrespondingEntity;
import com.company.CompanyApp.app.annotations.ViewName;
import com.company.CompanyApp.app.dao.WorkerRepository;
import com.company.CompanyApp.app.dto.WorkerData;
import com.company.CompanyApp.app.entity.Worker;
import com.company.CompanyApp.app.enums.WorkerType;
import com.company.CompanyApp.exception.WorkerNotFoundException;
import com.company.CompanyApp.app.service.IWorkerService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;

@Service
public class WorkerService implements IWorkerService {
    private final WorkerRepository workerRepository;


    public WorkerService(@Qualifier("workerRepository")
                         WorkerRepository workerRepository) {

        this.workerRepository = workerRepository;
    }


    @Override
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


    @Override
    public <T extends Worker> T createWorker(WorkerType type) throws Exception {
        CorrespondingEntity entity = WorkerType.class.getField(type.name()).getAnnotation(CorrespondingEntity.class);

        if (entity == null) {
            throw new IllegalArgumentException("No CorrespondingEntity annotation found for WorkerType: " + type.name());
        }

        String classPath = String.format("%s.%s", entity.path(), entity.entityClass());

        try {
            //Specify that the class is a subclass of Worker
            Class<T> targetClass = (Class<T>) Class.forName(classPath);

            T worker = targetClass.getConstructor().newInstance();
            worker.setWorkerType(type);

            return worker;
        } catch (Exception ex) {
           throw new Exception("Cannot create a new instance of " + ex);
        }

    }


    @Override
    public void mergeWorkersFieldsData(List<WorkerData> to, List<WorkerData> from) {
        for (int i = 0; i < from.size(); i++) {
            if (from.get(i).getField().equals(to.get(i).getField())) {
                to.get(i).setValue(from.get(i).getValue());
            } else break;
        }
    }


    @Override
    public List<WorkerData> getWorkersFields(Object worker) throws IllegalAccessException {
        List<WorkerData> forView = new LinkedList<>();
        Field[] base = worker.getClass().getSuperclass().getDeclaredFields();
        Field[] extended = worker.getClass().getDeclaredFields();

        addFieldsForView(forView, base, worker);
        addFieldsForView(forView, extended, worker);

        return forView;
    }


    //CRUD
    @Override
    public Worker getWorker(int id) throws WorkerNotFoundException {
        var worker = workerRepository.findById(id);

        if (worker.isPresent()) return (Worker)worker.get();
        else throw new WorkerNotFoundException(id);
    }


    @Override
    public void save(Worker worker) {
        workerRepository.save(worker);
    }


    @Override
    public void deleteWorker(Worker worker) {
        workerRepository.delete(worker);
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
}