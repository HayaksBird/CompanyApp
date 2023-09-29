package com.company.CompanyApp.app.service;

import com.company.CompanyApp.app.dto.WorkerData;
import com.company.CompanyApp.app.entity.Worker;
import com.company.CompanyApp.app.enums.WorkerType;
import com.company.CompanyApp.exception.WorkerNotFoundException;

import java.lang.annotation.Annotation;
import java.util.List;

public interface IWorkerService {
    public <T extends Worker> T getWorkerExtObject(Worker worker)
            throws ClassNotFoundException, NoSuchFieldException;

    List<WorkerData> getWorkersFields(Object worker) throws IllegalAccessException;

    void mergeWorkersFieldsData(List<WorkerData> to, List<WorkerData> from);

    <T extends Worker> T createWorker(WorkerType type) throws Exception;

    Worker getWorker(int id) throws WorkerNotFoundException;

    <T extends Worker> boolean isAnnotated(Class<?> workerClass,
                                           T worker,
                                           String fieldName,
                                           Class<? extends Annotation> annotation) throws NoSuchFieldException;

    //CRUD
    void save(Worker worker);

    void deleteWorker(Worker worker);
}
