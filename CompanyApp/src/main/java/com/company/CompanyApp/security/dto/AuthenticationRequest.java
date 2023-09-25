package com.company.CompanyApp.security.dto;

import com.company.CompanyApp.validation.annotations.Numeric;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * This class encapsulates the data from the request body of
 * an authentication request.
 */
public class AuthenticationRequest {
    @Numeric(message = "id must be numeric")
    @NotNull(message = "Provide an id")
    private String  id;
    @NotNull(message = "Provide a password")
    private String password;
    private List<String> errorMessages;


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

    public List<String> getErrorMessages() {
        return errorMessages;
    }

    public void setErrorMessages(List<String> errorMessages) {
        this.errorMessages = errorMessages;
    }
}
