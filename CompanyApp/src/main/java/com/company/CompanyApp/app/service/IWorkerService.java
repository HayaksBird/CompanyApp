package com.company.CompanyApp.app.service;

import com.company.CompanyApp.app.entity.Worker;
import com.company.CompanyApp.exception.WorkerNotFoundException;

public interface IWorkerService {
    Worker getWorker(int id) throws WorkerNotFoundException;

    void updateWorker(Worker worker);
}
