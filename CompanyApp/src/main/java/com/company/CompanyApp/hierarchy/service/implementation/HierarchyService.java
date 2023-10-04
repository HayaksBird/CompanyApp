package com.company.CompanyApp.hierarchy.service.implementation;

import com.company.CompanyApp.app.annotations.CorrespondingEntity;
import com.company.CompanyApp.app.entity.worker.Worker;
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


    //CONSTRUCTORS
    public HierarchyService(IWorkerService workerService) {
        this.workerService = workerService;
    }


    /**
     *
     */
    @Override
    public boolean isSubordinateWorkerType(WorkerType workerType, HashMap<String, WorkerType> subordinateWorkerTypes) throws NoSuchFieldException {
        CorrespondingEntity entity = WorkerType.class.getField(workerType.name()).getAnnotation(CorrespondingEntity.class);
        WorkerType retrievedType = subordinateWorkerTypes.get(entity.entityClass());

        return retrievedType != null;
    }


    /**
     * This method extracts the roles of the currently logged-in user.
     */
    @Override
    public HashSet<String> getLoggedUsersRoles() {
        HashSet<String> loggedUsersRoles = new HashSet<>();
        var roles = SecurityContextHolder.getContext().getAuthentication().getAuthorities();

        for (var role : roles) {
            loggedUsersRoles.add(role.toString().substring(5));
        }

        return loggedUsersRoles;
    }


    /**
     * Get a list of roles of a provided worker.
     */
    @Override
    public <T extends Worker> List<String> getRoles(Worker worker) throws Exception {
        String maxRole = getMaxRole(worker);
        return accumulateRoles(maxRole);
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
    public HashMap<String, WorkerType> getSubordinateWorkerTypes(int maxRolePos) throws NoSuchFieldException {
        HashMap<String, WorkerType> subordinateWorkerTypes = new HashMap<>();

        for (var workerType : WorkerType.values()) {
            CorrespondingEntity entity = WorkerType.class.getField(workerType.name()).getAnnotation(CorrespondingEntity.class);
            int count = 0, minCount = 0;

            for (var allRoles : workerType.getRoles().entrySet()) {
                count = Hierarchy.valueOf(allRoles.getValue()).ordinal() + 1;

                if (count < minCount || minCount == 0) minCount = count;
            }

            if (maxRolePos > minCount && maxRolePos > count) {
                subordinateWorkerTypes.put(entity.entityClass(), workerType);
            }
        }

        return subordinateWorkerTypes;
    }


    @Override
    public List<String> getSubordinateWorkerTypesList(HashMap<String, WorkerType> subordinateWorkerTypes) {
        List<String> subordinateWorkerTypesList = new LinkedList<>();

        for (var entityName : subordinateWorkerTypes.entrySet()) {
            subordinateWorkerTypesList.add(entityName.getKey());
        }

        return subordinateWorkerTypesList;
    }


    /**
     * Get the max role of the worker.
     *
     * NOTE: If the worker is position type based, then an extended (complete)
     * version of the worker will be acquired to determine the roles.
     */
    private <T extends Worker> String getMaxRole(Worker worker) throws Exception {
        String maxRole;

        var allRoles = worker.getWorkerType().getRoles();

        if (allRoles.size() == 1) maxRole = allRoles.get("");
        //Max role could only be determined by knowing the position type
        else {
            T extWorker;
            Field posTypeField;
            String posType;

            extWorker = workerService.getWorkerExtObject(worker);
            posTypeField = extWorker.getClass().getDeclaredField(worker.getWorkerType().getPosTypeFieldName());
            posTypeField.setAccessible(true);
            posType = posTypeField.get(extWorker).toString();

            maxRole = allRoles.get(posType);
        }

        if (maxRole != null) return maxRole;

        throw new Exception("Requested position is not found: " + worker.getWorkerType().getPosTypeFieldName());
    }


    /**
     * Accumulate the roles from the lowest to the requested one
     */
    private List<String> accumulateRoles(String maxRole) {
        List<String> roles = new LinkedList<>();

        for (Hierarchy role : Hierarchy.values()) {
            roles.add(role.name());

            if (role.name().equals(maxRole)) break;
        }

        return roles;
    }


    /**
     * Enum with all possible roles in the app
     * From the lowest role (top) to the biggest (bottom)
     *
     * NOTE: Order matters!
     */
    private enum Hierarchy {
        ROLE_D,
        ROLE_C,
        ROLE_B,
        ROLE_A
    }
}