package com.company.CompanyApp.security.service;

import com.company.CompanyApp.entity.user.User;
import com.company.CompanyApp.entity.worker.Worker;

/**
 * The class implementing this interface is responsible for providing
 * access to the user data un the database (including their roles).
 */
public interface IUserService {
    /**
     * Add a user to the database.
     */
    void addUser(User user);


    /**
     * Get user by their ID.
     */
    User getUser(int id);


    User createUser(Worker worker);
}
