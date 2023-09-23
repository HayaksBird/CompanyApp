package com.company.CompanyApp.app.controller;

import com.company.CompanyApp.app.entity.Worker;
import com.company.CompanyApp.app.service.IWorkerService;
import com.company.CompanyApp.app.service.WorkerManager;
import com.company.CompanyApp.exception.WorkerNotFoundException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.HashSet;

/**
 * Made this bean lazy, because it requires user's data from the SecurityContextHolder.
 * Thus, this bean must be created after the jwt filter is ran.
 */
@Lazy
@Configuration
public class UserContextConfig <T extends Worker> {
    private final IWorkerService workerService;
    private static HashSet<String> roles;


    public UserContextConfig(IWorkerService workerService) {
        this.workerService = workerService;

        roles = new HashSet<>();
        addUserRoles();
    }


    /**
     *
     */
    @Bean
    public T userContext() throws NoSuchFieldException, ClassNotFoundException, WorkerNotFoundException {
        T loggedUser;

        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        loggedUser = WorkerManager.getWorkerExtObject(workerService.getWorker(Integer.parseInt(id)));

        return loggedUser;
    }


    /**
     * This method extracts the roles of the currently logged-in user.
     */
    private static void addUserRoles() {
        var roles = SecurityContextHolder.getContext().getAuthentication().getAuthorities();

        for (var role : roles) {
            UserContextConfig.roles.add(role.toString().substring(5));
        }
    }


    //SETTERS & GETTERS
    public static HashSet<String> getRoles() {
        return roles;
    }
}
