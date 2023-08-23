package com.company.CompanyApp.security.dto;

/**
 * This class encapsulates the data from the request body of
 * an authentication request.
 */
public class AuthenticationRequest {
    private String id, password;


    //CONSTRUCTORS
    public AuthenticationRequest() {}


    //Getters & Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
