package com.company.CompanyApp.hierarchy.service;

import com.company.CompanyApp.app.entity.Worker;
import com.company.CompanyApp.app.enums.WorkerType;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public interface IHierarchyService {
    void addLoggedUserRoles();

    <T extends Worker> List<String> getRoles(Worker worker) throws Exception;

    void setSubordinateWorkerTypes(int maxRolePos) throws NoSuchFieldException;

    HashSet<String> getLoggedUsersRoles();

    HashMap<String, WorkerType> getSubordinateWorkerTypes();

    List<String> getSubordinateWorkerTypesList();
}
