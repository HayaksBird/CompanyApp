package com.company.CompanyApp.controller;

import com.company.CompanyApp.entity.worker.Worker;
import com.company.CompanyApp.service.IDepartmentService;
import com.company.CompanyApp.service.IWorkerService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
public class UserContextConfig {
    private final IWorkerService workerService;
    private final IDepartmentService departmentService;


    public UserContextConfig(IWorkerService workerService,
                             IDepartmentService departmentService) {

        this.departmentService = departmentService;
        this.workerService = workerService;
    }


    @Lazy
    @Bean
    public Worker userContext() {
        Worker loggedUser;

        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        loggedUser = workerService.getWorker(Integer.parseInt(id));

        return loggedUser;
    }

    /*
    @Lazy
    @Bean
    public Department departmentContext(Worker loggedUser) {
        return departmentService.getDepartment(loggedUser.getDepartmentId());
    }
     */
}
