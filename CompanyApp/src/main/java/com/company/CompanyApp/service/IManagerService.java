package com.company.CompanyApp.service;

import com.company.CompanyApp.entity.Manager;

import java.util.List;

public interface IManagerService {
    Manager getManager(int id);


    List<Manager> getAllManagers();


    void addManager(Manager manager);


    void updateManager(Manager manager);


    void deleteManager(int id);


    void deleteAllManagers();
}
