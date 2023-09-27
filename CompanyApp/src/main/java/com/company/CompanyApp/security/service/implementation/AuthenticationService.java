package com.company.CompanyApp.security.service.implementation;

import com.company.CompanyApp.app.service.IWorkerService;
import com.company.CompanyApp.hierarchy.service.IHierarchyService;
import com.company.CompanyApp.security.entity.User;
import com.company.CompanyApp.app.entity.Worker;
import com.company.CompanyApp.exception.WorkerNotFoundException;
import com.company.CompanyApp.security.dto.AuthenticationRequest;
import com.company.CompanyApp.security.service.IAuthenticationService;
import com.company.CompanyApp.security.service.IGmailService;
import com.company.CompanyApp.security.service.IJwtService;
import com.company.CompanyApp.security.service.IUserService;
import jakarta.servlet.http.Cookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * The implementation class of the authentication service.
 */
@Service
public class AuthenticationService implements IAuthenticationService {
    private final IUserService userService;
    private final PasswordEncoder passwordEncoder;
    private final IWorkerService workerService;
    private final IJwtService jwtService;
    private final IGmailService gmailService;
    private final IHierarchyService hierarchyService;
    private final AuthenticationManager authenticationManager;
    private Worker worker = null;
    private String validationCode;


    //CONSTRUCTORS
    public AuthenticationService(IUserService userService,
                                 PasswordEncoder passwordEncoder,
                                 AuthenticationManager authenticationManager,
                                 IWorkerService workerService,
                                 IGmailService gmailService,
                                 IJwtService jwtService,
                                 IHierarchyService hierarchyService) {

        this.hierarchyService = hierarchyService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.gmailService = gmailService;
        this.jwtService = jwtService;
        this.workerService = workerService;
    }


    /**
     * This method authenticates an already existing user.
     * TO BE IMPLEMENTED:
     * After the successful authentication it generates and returns a JWT.
     */
    @Override
    public String authenticate(AuthenticationRequest request) {
        Authentication authentication;

        try {
            authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    request.getId(),
                    request.getPassword()
                )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            return null;
        } catch (AuthenticationException ex) {
            return "Wrong username and/or password!";
        }
    }


    /**
     *
     */
    @Override
    public String sendValidationCode(String id) {
        int userId = Integer.parseInt(id);
        String gmail;
        String validCode;

        try {
            worker = workerService.getWorker(userId);
        } catch (WorkerNotFoundException ex) {
            return ex.getMessage();
        }
        gmail = worker.getEmail();

        validCode = UUID.randomUUID().toString().substring(0, 11);

        gmailService.sendGmail(gmail,
                "Register to Company app",
                "Enter this code in the registration window: " + validCode);

        validationCode = validCode;
        return null;
    }


    /**
     *
     */
    @Override
    public void register(AuthenticationRequest request)
                        throws NoSuchFieldException, ClassNotFoundException, IllegalAccessException {

        User user = new User();

        user.setId(worker.getId());
        user.setType(worker.getWorkerType());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(hierarchyService.getRoles(worker));

        userService.addUser(user);

        SecurityContextHolder.getContext().setAuthentication(
            new UsernamePasswordAuthenticationToken(
                    request.getId(),
                    null,
                    user.getAuthorities()
            )
        );
    }


    /**
     *
     */
    @Override
    public Cookie generateJwtCookie() {
        String jwt;
        Cookie jwtCookie;
        Authentication userAuth;

        userAuth = SecurityContextHolder.getContext().getAuthentication();
        jwt = getJwt(userAuth);

        jwtCookie = new Cookie("jwt", jwt);
        jwtCookie.setMaxAge((int) jwtService.getLifespan() / 1000);  //Pass in seconds
        jwtCookie.setDomain("localhost");
        jwtCookie.setPath("/");
        jwtCookie.setHttpOnly(true);

        return jwtCookie;
    }


    /**
     *
     */
    private String getJwt(Authentication userAuth) {
        String roles;
        Map<String, Object> extraClaims =  new HashMap<>();

        roles = userAuth.getAuthorities().toString();
        extraClaims.put("roles", roles.substring(1, roles.length() - 1).split(", "));

        return jwtService.generateToken(extraClaims, Authentication::getName, userAuth);
    }


    //Setters & Getters
    @Override
    public String getValidationCode() {
        return validationCode;
    }
}
