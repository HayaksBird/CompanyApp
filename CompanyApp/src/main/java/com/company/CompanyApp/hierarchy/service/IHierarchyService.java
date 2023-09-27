package com.company.CompanyApp.hierarchy.service;

import com.company.CompanyApp.app.entity.Worker;
import com.company.CompanyApp.app.enums.WorkerType;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface IHierarchyService {
    <T extends Worker> String[] getRoles(Worker worker)
                                throws NoSuchFieldException, ClassNotFoundException, IllegalAccessException;


    void addLoggedUserRoles();


    void setSubordinateWorkerTypes(Set<String> roles) throws NoSuchFieldException;


    HashSet<String> getLoggedUsersRoles();


    HashMap<String, WorkerType> getSubordinateWorkerTypes();


    List<String> getSubordinateWorkerTypesList();
}
