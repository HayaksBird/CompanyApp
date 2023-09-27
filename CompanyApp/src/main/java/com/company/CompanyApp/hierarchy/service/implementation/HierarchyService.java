package com.company.CompanyApp.hierarchy.service.implementation;

import com.company.CompanyApp.app.annotations.CorrespondingEntity;
import com.company.CompanyApp.app.annotations.Role;
import com.company.CompanyApp.app.entity.Worker;
import com.company.CompanyApp.app.enums.WorkerType;
import com.company.CompanyApp.hierarchy.service.IHierarchyService;
import com.company.CompanyApp.app.service.IWorkerService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.*;

/**
 * This class manages the hierarchy of a worker in a company.
 * It provides a diverse functionality from establishing the
 * worker's place in hierarchy (roles) to identifying all the
 * other worker types that are considered being subordinate to him.
 */
@Service
public class HierarchyService implements IHierarchyService {
    private final IWorkerService workerService;
    private HashSet<String> loggedUsersRoles;
    //Subordinate worker types for the currently logged-in user
    private HashMap<String, WorkerType> subordinateWorkerTypes;
    private List<String> subordinateWorkerTypesList;


    //CONSTRUCTORS
    public HierarchyService(IWorkerService workerService) {
        this.workerService = workerService;
    }


    /**
     * This method extracts the roles of the currently logged-in user.
     */
    @Override
    public void addLoggedUserRoles() {
        loggedUsersRoles = new HashSet<>();
        var roles = SecurityContextHolder.getContext().getAuthentication().getAuthorities();

        for (var role : roles) {
            this.loggedUsersRoles.add(role.toString().substring(5));
        }
    }


    /**
     * Get an array of roles of a provided worker.
     *
     * NOTE: If the worker is position type based, then an extended (complete)
     * version of the worker will be acquired to determine the roles.
     */
    @Override
    public <T extends Worker> String[] getRoles(Worker worker)
                                       throws NoSuchFieldException, ClassNotFoundException, IllegalAccessException {

        String[] roles;
        String fieldName = worker.getWorkerType().name();
        Role rolesAnnotation = WorkerType.class.getField(fieldName).getAnnotation(Role.class);

        if (rolesAnnotation.isPositionTypeBased()) {
            T extWorker = workerService.getWorkerExtObject(worker);
            roles = getTypeBasedRoles(extWorker, rolesAnnotation);
        } else roles = rolesAnnotation.roles();

        return roles;
    }


    /**
     * Set a list of all subordinate worker types. This operation is executed based
     * on the roles provided. Since the application's hierarchy logic is in an accumulative
     * fashion (meaning, the more roles you have, the higher is your hierarchy position), all
     * the worker types that have fewer roles, will be considered subordinate.
     *
     * NOTE: If a worker type is position type based (meaning, its set of roles depend on the
     * position type), then that type is considered being subordinate if at least one of
     * its possible set of roles is smaller than the provided set of roles.
     */
    @Override
    public void setSubordinateWorkerTypes(Set<String> roles) throws NoSuchFieldException {
        subordinateWorkerTypes = new HashMap<>();
        subordinateWorkerTypesList = new LinkedList<>();

        for (WorkerType workerType : WorkerType.values()) {
            int minCounter = 0;
            int counter = 0;
            Role role = WorkerType.class.getField(workerType.name()).getAnnotation(Role.class);
            CorrespondingEntity entity = WorkerType.class.getField(workerType.name()).getAnnotation(CorrespondingEntity.class);

            /*
            A worker type is considered to be subordinate if all of its possible
            types have lower roles.
             */
            for (String workerRole : role.roles()) {
                if (workerRole.startsWith("ROLE_")) counter++;
                else {
                    if (counter < minCounter || minCounter == 0) minCounter = counter;
                    counter = 0;
                }
            }

            if (roles.size() > counter && roles.size() > minCounter) {
                subordinateWorkerTypes.put(entity.entityClass(), workerType);
                subordinateWorkerTypesList.add(entity.entityClass());
            }
        }
    }


    /**
     * If worker's roles could not be determined purely on its worker type
     * attribute, then it means that we must acquire his specific position type to
     * determine his set of roles.
     */
    private String[] getTypeBasedRoles(Object extWorker,
                                       Role rolesAnnotation)
                                       throws NoSuchFieldException, IllegalAccessException {

        String type;
        boolean insert = false;
        List<String> roles = new ArrayList<>();
        Field field = extWorker.getClass().getDeclaredField(rolesAnnotation.typeField());

        field.setAccessible(true);
        type = field.get(extWorker).toString();

        for (String role : rolesAnnotation.roles()) {
            if (!role.startsWith("ROLE_") && insert) break;

            if (insert) roles.add(role);

            if (role.equals(type)) insert = true;
        }

        return roles.toArray(new String[0]);
    }


    //GETTERS
    @Override
    public HashSet<String> getLoggedUsersRoles() {
        return loggedUsersRoles;
    }

    @Override
    public HashMap<String, WorkerType> getSubordinateWorkerTypes() {
        return subordinateWorkerTypes;
    }

    @Override
    public List<String> getSubordinateWorkerTypesList() {
        return subordinateWorkerTypesList;
    }
}