package com.company.CompanyApp.security.service.implementation;

import com.company.CompanyApp.dao.UserRepository;
import com.company.CompanyApp.entity.user.Role;
import com.company.CompanyApp.entity.user.User;
import com.company.CompanyApp.entity.worker.DataAnalyst;
import com.company.CompanyApp.entity.worker.SoftwareDeveloper;
import com.company.CompanyApp.entity.worker.Worker;
import com.company.CompanyApp.enums.RoleType;
import com.company.CompanyApp.enums.WorkerType;
import com.company.CompanyApp.security.service.IUserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * The implementation class of the user service.
 */
@Service
public class UserService implements IUserService {
    private final UserRepository userRepository;


    //CONSTRUCTORS
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public void addUser(User user) {
        userRepository.save(user);
    }


    /**
     * Get user by their username.
     */
    @Override
    public User getUser(int id) {
        var user = userRepository.findById(id);

        return user.orElse(null);
    }


    /**
     *
     */
    @Override
    public User createUser(Worker worker) {
        User user = new User();
        int lim = 0;

        user.setId(worker.getId());
        user.setType(worker.getWorkerType());

        if (worker.getWorkerType() == WorkerType.ADMIN) {
            lim = 4;
        } else if (worker.getWorkerType() == WorkerType.SOFTWARE_DEVELOPER) {

            switch(((SoftwareDeveloper) worker).getPositionType()) {
                case JUNIOR -> lim = 1;
                case SENIOR -> lim = 2;
            }

        } else if (worker.getWorkerType() == WorkerType.DATA_ANALYST) {

            switch(((DataAnalyst) worker).getPositionType()) {
                case JUNIOR -> lim = 1;
                case SENIOR -> lim = 2;
            }

        } else if (worker.getWorkerType() == WorkerType.INTERN) {
            lim = 1;
        } else if (worker.getWorkerType() == WorkerType.MANAGER) {
            lim = 3;
        }
        user.setRoles(addRoles(lim, worker.getId()));

        return user;
    }


    /**
     *
     */
    private List<Role> addRoles(int lim, int id) {
        List<Role> roles = new ArrayList<>();
        int count = 0;

        for (RoleType type : RoleType.values()) {
            roles.add(new Role(type, id));

            count++;
            if (count == lim) break;
        }

        return roles;
    }
}
