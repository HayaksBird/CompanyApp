package com.company.CompanyApp.app.service;

import com.company.CompanyApp.app.entity.worker.Worker;
import com.company.CompanyApp.app.enums.WorkerType;
import com.company.CompanyApp.exception.ItemNotFoundException;

public interface IWorkerService {
    public <T extends Worker> T getWorkerExtObject(Worker worker)
            throws ClassNotFoundException, NoSuchFieldException;

    <T extends Worker> T createWorker(WorkerType type) throws Exception;

    Worker getWorker(int id) throws ItemNotFoundException;

    //CRUD
    void save(Worker worker);

    void deleteWorker(Worker worker);
}
