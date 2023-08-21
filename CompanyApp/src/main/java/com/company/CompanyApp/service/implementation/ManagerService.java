package com.company.CompanyApp.service.implementation;

import com.company.CompanyApp.dao.ManagerRepository;
import com.company.CompanyApp.entity.Manager;
import com.company.CompanyApp.service.IManagerService;
import org.springframework.stereotype.Service;

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

        if (manager.isPresent()) return manager.get();
        else return null;
    }


    @Override
    public List<Manager> getAllManagers() {
        return managerRepository.findAll();
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
    }


    @Override
    public void deleteAllManagers() {
        managerRepository.deleteAll();
    }
}
