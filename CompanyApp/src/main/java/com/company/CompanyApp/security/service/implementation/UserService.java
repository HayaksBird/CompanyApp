package com.company.CompanyApp.security.service.implementation;

import com.company.CompanyApp.dao.UserRepository;
import com.company.CompanyApp.entity.User;
import com.company.CompanyApp.security.service.IUserService;
import org.springframework.stereotype.Service;

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
}
