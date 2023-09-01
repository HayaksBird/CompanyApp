package com.company.CompanyApp.service.implementation;

import com.company.CompanyApp.dao.WorkerRepository;
import com.company.CompanyApp.entity.Worker;
import com.company.CompanyApp.exception.WorkerkNotFoundException;
import com.company.CompanyApp.service.IWorkerService;
import org.springframework.stereotype.Service;

@Service
public class WorkerService implements IWorkerService {
    private final WorkerRepository workerRepository;


    public WorkerService(WorkerRepository workerRepository) {
        this.workerRepository = workerRepository;
    }


    @Override
    public Worker getWorker(int id) {
        var worker = workerRepository.findById(id);

        if (worker.isPresent()) return (Worker)worker.get();
        else throw new WorkerkNotFoundException(id);
    }
}
