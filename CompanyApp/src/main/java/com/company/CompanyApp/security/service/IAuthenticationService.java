package com.company.CompanyApp.security.service;

import com.company.CompanyApp.security.dto.AuthenticationRequest;
import jakarta.servlet.http.Cookie;

/**
 * The class implementing this interface is responsible for both registering
 * and authenticating a user. In both cases it returns a JWT to the user.
 */
public interface IAuthenticationService {

    /**
     * This method authenticates an already existing user.
     * TO BE IMPLEMENTED:
     * After the successful authentication it generates and returns a JWT.
     */
    String authenticate(AuthenticationRequest request);


    String sendValidationCode(String id);


    void register(AuthenticationRequest request) throws Exception;


    Cookie generateJwtCookie();


    public Cookie generateJwtCookieKiller();


    String getValidationCode();
}
