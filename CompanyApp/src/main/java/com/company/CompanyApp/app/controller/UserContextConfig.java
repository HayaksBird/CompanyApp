package com.company.CompanyApp.app.controller;

import com.company.CompanyApp.app.entity.worker.Worker;
import com.company.CompanyApp.app.enums.WorkerType;
import com.company.CompanyApp.exception.ItemNotFoundException;
import com.company.CompanyApp.hierarchy.service.IHierarchyService;
import com.company.CompanyApp.app.service.IWorkerService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Made this bean lazy, because it requires user's data from the SecurityContextHolder.
 * Thus, this bean must be created after the jwt filter is ran.
 */
@Lazy
@Configuration
public class UserContextConfig {
    private final IWorkerService workerService;
    private final IHierarchyService hierarchyService;



    //CONSTRUCTORS
    public UserContextConfig(IWorkerService workerService,
                             IHierarchyService hierarchyService) throws NoSuchFieldException {

        this.workerService = workerService;
        this.hierarchyService = hierarchyService;
    }


    /**
     *
     */
    @Bean
    public <T extends Worker> T userContext() throws NoSuchFieldException, ClassNotFoundException, ItemNotFoundException {
        T loggedUser;

        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        loggedUser = workerService.getWorkerExtObject(workerService.getWorker(Integer.parseInt(id)));

        return loggedUser;
    }


    /**
     *
     */
    @Bean
    public UserContextData userContextData() throws NoSuchFieldException {
        var loggedUsersRoles = hierarchyService.getLoggedUsersRoles();
        var subordinateWorkerTypes = hierarchyService.getSubordinateWorkerTypes(loggedUsersRoles.size());
        var subordinateWorkerTypesList = hierarchyService.getSubordinateWorkerTypesList(subordinateWorkerTypes);

        return new UserContextData(loggedUsersRoles, subordinateWorkerTypes, subordinateWorkerTypesList);
    }


    /**
     * Metadata for the logged-in user. Contains all subordinate worker types for that
     * user and a set of his roles.
     */
    public class UserContextData {
        private final HashSet<String> loggedUsersRoles;
        private final HashMap<String, WorkerType> subordinateWorkerTypes;
        private final List<String> subordinateWorkerTypesList;



        //CONSTRUCTORS
        public UserContextData(HashSet<String> loggedUsersRoles,
                               HashMap<String, WorkerType> subordinateWorkerTypes,
                               List<String> subordinateWorkerTypesList) {

            this.loggedUsersRoles = loggedUsersRoles;
            this.subordinateWorkerTypes = subordinateWorkerTypes;
            this.subordinateWorkerTypesList = subordinateWorkerTypesList;
        }


        //Getters
        public HashSet<String> getLoggedUsersRoles() {
            return loggedUsersRoles;
        }

        public HashMap<String, WorkerType> getSubordinateWorkerTypes() {
            return subordinateWorkerTypes;
        }

        public List<String> getSubordinateWorkerTypesList() {
            return subordinateWorkerTypesList;
        }
    }
}
