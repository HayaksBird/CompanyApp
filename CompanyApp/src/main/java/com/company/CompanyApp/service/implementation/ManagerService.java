package com.company.CompanyApp.service.implementation;

import com.company.CompanyApp.dao.ManagerRepository;
import com.company.CompanyApp.entity.Manager;
import com.company.CompanyApp.entity.Worker;
import com.company.CompanyApp.exception.WorkerkNotFoundException;
import com.company.CompanyApp.service.IManagerService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ManagerService implements IManagerService {
    private final ManagerRepository managerRepository;


    public ManagerService(ManagerRepository managerRepository) {
        this.managerRepository = managerRepository;
    }


    @Override
    public Manager getManager(int id) {
        var manager = managerRepository.findById(id);

        if (manager.isPresent()) return (Manager)manager.get();
        else throw new WorkerkNotFoundException(id);
    }


    @Override
    public List<Manager> getAllManagers() {
        return listToManager(managerRepository.findAll());
    }


    @Override
    public void addManager(Manager manager) {
        managerRepository.save(manager);
    }


    @Override
    public void updateManager(Manager manager) {
        if (managerRepository.findById(manager.getDepartmentId()).isPresent()) {
            managerRepository.save(manager);
        }
    }


    @Override
    public void deleteManager(int id) {
        var manager = managerRepository.findById(id);

        if (manager.isPresent()) managerRepository.delete(manager.get());
        else throw new WorkerkNotFoundException(id);
    }


    @Override
    public void deleteAllManagers() {
        managerRepository.deleteAll();
    }


    /**
     *
     */
    private List<Manager> listToManager(List<Worker> workers) {
        List<Manager> managers = new ArrayList<Manager>();

        for (Worker worker : workers) {
            managers.add((Manager)worker);
        }

        return managers;
    }
}
