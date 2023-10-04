package com.company.CompanyApp.app.service.implementation;

import com.company.CompanyApp.app.annotations.CorrespondingEntity;
import com.company.CompanyApp.app.dao.WorkerRepository;
import com.company.CompanyApp.app.entity.worker.Worker;
import com.company.CompanyApp.app.enums.WorkerType;
import com.company.CompanyApp.app.service.IWorkerService;
import com.company.CompanyApp.exception.ItemNotFoundException;
import com.company.CompanyApp.security.dao.UserRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class WorkerService implements IWorkerService {
    private final WorkerRepository workerRepository;


    public WorkerService(@Qualifier("workerRepository")
                         WorkerRepository workerRepository) {

        this.workerRepository = workerRepository;
    }


    /**
     *
     */
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


    /**
     *
     */
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


    //CRUD
    @Override
    public Worker getWorker(int id) throws ItemNotFoundException {
        var worker = workerRepository.findById(id);

        if (worker.isPresent()) return (Worker)worker.get();
        else throw new ItemNotFoundException(Worker.class, id);
    }


    @Override
    public void save(Worker worker) {
        workerRepository.save(worker);
    }


    @Override
    public void deleteWorker(Worker worker) {
        try {
            workerRepository.delete(worker);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}