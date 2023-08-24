package com.company.CompanyApp.security.service.implementation;

import com.company.CompanyApp.entity.User;
import com.company.CompanyApp.exception.BadLoginInputException;
import com.company.CompanyApp.exception.WorkerkNotFoundException;
import com.company.CompanyApp.security.dto.AuthenticationRequest;
import com.company.CompanyApp.security.service.IAuthenticationService;
import com.company.CompanyApp.security.service.IGmailService;
import com.company.CompanyApp.security.service.IUserService;
import com.company.CompanyApp.service.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.UUID;

/**
 * The implementation class of the authentication service.
 */
@Service
public class AuthenticationService implements IAuthenticationService {
    private final IUserService userService;
    private final PasswordEncoder passwordEncoder;
    private final IEmployeeService employeeService;
    private final IManagerService managerService;
    private final IGmailService gmailService;
    private final AuthenticationManager authenticationManager;


    //CONSTRUCTORS
    public AuthenticationService(IUserService userService,
                                 PasswordEncoder passwordEncoder,
                                 AuthenticationManager authenticationManager,
                                 IEmployeeService employeeService,
                                 IManagerService managerService,
                                 IGmailService gmailService) {

        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.employeeService = employeeService;
        this.managerService = managerService;
        this.gmailService = gmailService;
    }


    /**
     * This method authenticates an already existing user.
     * TO BE IMPLEMENTED:
     * After the successful authentication it generates and returns a JWT.
     */
    @Override
    public void authenticate(AuthenticationRequest request) throws BadLoginInputException {
        Authentication authentication;

        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getId(),
                            request.getPassword()
                    )
            );
        } catch (AuthenticationException ex) {
            throw new BadLoginInputException();
        }


        
    }


    /**
     *
     */
    @Override
    public void register(AuthenticationRequest request) {
        User user = new User();

        user.setId(Integer.parseInt(request.getId()));
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles();

        userService.addUser(user);
    }


    /**
     *
     */
    @Override
    public String getValidationCode(String id) throws WorkerkNotFoundException {
        int userId = Integer.parseInt(id);
        String gmail;
        String validCode;

        if (userId < 100) {
            gmail = managerService.getManager(userId).getEmail();
        } else {
            gmail = employeeService.getEmployee(userId).getEmail();
        }

        validCode = UUID.randomUUID().toString().substring(0, 11);

        gmailService.sendGmail(gmail,
                        "Register to Company app",
                        "Enter this code in the registration window: " + validCode);

        return validCode;
    }
}
