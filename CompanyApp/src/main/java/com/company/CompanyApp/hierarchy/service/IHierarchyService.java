package com.company.CompanyApp.hierarchy.service;

import com.company.CompanyApp.app.entity.worker.Worker;
import com.company.CompanyApp.app.enums.WorkerType;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public interface IHierarchyService {
    public boolean isSubordinateWorkerType(WorkerType workerType,
                                           HashMap<String, WorkerType> subordinateWorkerTypes) throws NoSuchFieldException;
    HashSet<String> getLoggedUsersRoles();

    <T extends Worker> List<String> getRoles(Worker worker) throws Exception;

    public HashMap<String, WorkerType> getSubordinateWorkerTypes(int maxRolePos) throws NoSuchFieldException;

    public List<String> getSubordinateWorkerTypesList(HashMap<String, WorkerType> subordinateWorkerTypes);
}
