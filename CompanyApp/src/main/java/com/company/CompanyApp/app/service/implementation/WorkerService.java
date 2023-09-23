package com.company.CompanyApp.app.service.implementation;

import com.company.CompanyApp.app.dao.WorkerRepository;
import com.company.CompanyApp.app.entity.Worker;
import com.company.CompanyApp.exception.WorkerNotFoundException;
import com.company.CompanyApp.app.service.IWorkerService;
import org.springframework.stereotype.Service;

@Service
public class WorkerService implements IWorkerService {
    private final WorkerRepository workerRepository;


    public WorkerService(WorkerRepository workerRepository) {
        this.workerRepository = workerRepository;
    }


    @Override
    public Worker getWorker(int id) throws WorkerNotFoundException {
        var worker = workerRepository.findById(id);

        if (worker.isPresent()) return (Worker)worker.get();
        else throw new WorkerNotFoundException(id);
    }


    @Override
    public void updateWorker(Worker worker) {
        workerRepository.save(worker);
    }
}
