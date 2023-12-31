package com.company.CompanyApp.security.service;

import com.company.CompanyApp.security.entity.User;

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
}
