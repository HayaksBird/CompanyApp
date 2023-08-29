package com.company.CompanyApp.security.dto;

import com.company.CompanyApp.security.validation.annotations.Numeric;
import jakarta.validation.constraints.NotNull;

/**
 * This class encapsulates the data from the request body of
 * an authentication request.
 */
public class AuthenticationRequest {
    @Numeric(message = "Id must be numeric")
    @NotNull(message = "Provide an id")
    private String  id;
    @NotNull(message = "Provide a password")
    private String password;
    private String error;


    //CONSTRUCTORS
    public AuthenticationRequest() {
        error = "";
    }


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

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
