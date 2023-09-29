package com.company.CompanyApp.app.controller;

import com.company.CompanyApp.app.entity.Worker;
import com.company.CompanyApp.hierarchy.service.IHierarchyService;
import com.company.CompanyApp.app.service.IWorkerService;
import com.company.CompanyApp.exception.WorkerNotFoundException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Made this bean lazy, because it requires user's data from the SecurityContextHolder.
 * Thus, this bean must be created after the jwt filter is ran.
 */
@Lazy
@Configuration
public class UserContextConfig {
    private final IWorkerService workerService;


    //CONSTRUCTORS
    public UserContextConfig(IWorkerService workerService,
                             IHierarchyService hierarchyManagementService) throws NoSuchFieldException {

        this.workerService = workerService;

        hierarchyManagementService.addLoggedUserRoles();
        hierarchyManagementService.setSubordinateWorkerTypes(hierarchyManagementService.getLoggedUsersRoles().size());
    }


    /**
     *
     */
    @Bean
    public <T extends Worker> T userContext() throws NoSuchFieldException, ClassNotFoundException, WorkerNotFoundException {
        T loggedUser;

        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        loggedUser = workerService.getWorkerExtObject(workerService.getWorker(Integer.parseInt(id)));

        return loggedUser;
    }
}
