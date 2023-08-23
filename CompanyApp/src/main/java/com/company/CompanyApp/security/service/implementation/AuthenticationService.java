package com.company.CompanyApp.security.service.implementation;

import com.company.CompanyApp.security.dto.AuthenticationRequest;
import com.company.CompanyApp.security.service.IAuthenticationService;
import com.company.CompanyApp.security.service.IUserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * The implementation class of the authentication service.
 */
@Service
public class AuthenticationService implements IAuthenticationService {
    private final IUserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    //CONSTRUCTORS
    public AuthenticationService(IUserService userService,
                                 PasswordEncoder passwordEncoder,
                                 AuthenticationManager authenticationManager) {

        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }


    /**
     * This method authenticates an already existing user.
     * TO BE IMPLEMENTED:
     * After the successful authentication it generates and returns a JWT.
     */
    @Override
    public void authenticate(AuthenticationRequest request) {
        Authentication authentication;

        authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getId(),
                        request.getPassword()
                )
        );
    }
}
