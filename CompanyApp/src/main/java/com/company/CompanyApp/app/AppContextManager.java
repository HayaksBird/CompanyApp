package com.company.CompanyApp.app;

import com.company.CompanyApp.app.entity.worker.Worker;
import com.company.CompanyApp.app.enums.WorkerType;
import com.company.CompanyApp.exception.ItemNotFoundException;
import com.company.CompanyApp.hierarchy.service.IHierarchyService;
import com.company.CompanyApp.app.service.IWorkerService;
import org.springframework.beans.factory.support.DefaultSingletonBeanRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * Made this bean lazy, because it requires user's data from the SecurityContextHolder.
 *
 * Manages the context information about the user for the controllers.
 */
@Lazy
@Configuration
public class AppContextManager {
    private final IWorkerService workerService;
    private final IHierarchyService hierarchyService;
    private final ApplicationContext applicationContext;


    //CONSTRUCTORS
    public AppContextManager(IWorkerService workerService,
                             IHierarchyService hierarchyService,
                             ApplicationContext applicationContext) throws NoSuchFieldException {

        this.workerService = workerService;
        this.hierarchyService = hierarchyService;
        this.applicationContext = applicationContext;
    }


    /**
     * Create a bean out of the logged-in user, so that different controllers can
     * work with it.
     */
    @Bean
    public <T extends Worker> T userContext() throws NoSuchFieldException, ClassNotFoundException, ItemNotFoundException {
        T loggedUser;

        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        loggedUser = workerService.getWorkerExtObject(workerService.getWorker(Integer.parseInt(id)));

        return loggedUser;
    }


    /**
     * Create a container bean of user's specific data information.
     */
    @Bean
    public UserContextData userContextData() throws NoSuchFieldException {
        var loggedUsersRoles = hierarchyService.getLoggedUsersRoles();
        var subordinateWorkerTypes = hierarchyService.getSubordinateWorkerTypes(loggedUsersRoles.size());
        var subordinateWorkerTypesList = hierarchyService.getSubordinateWorkerTypesList(subordinateWorkerTypes);

        return new UserContextData(loggedUsersRoles, subordinateWorkerTypes, subordinateWorkerTypesList);
    }


    /**
     * Delete all context dependent beans from the spring container.
     */
    public void killContextDependentBeans() {
        Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation(Lazy.class);
        DefaultSingletonBeanRegistry registry = (DefaultSingletonBeanRegistry) applicationContext.getAutowireCapableBeanFactory();

        for (var bean : beansWithAnnotation.entrySet()) {
            if (bean.getValue().getClass().getPackage().getName().startsWith("com.company.CompanyApp")) {
                registry.destroySingleton(bean.getKey());
            }
        }
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