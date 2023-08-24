package com.company.CompanyApp.controller;

import com.company.CompanyApp.exception.BadLoginInputException;
import com.company.CompanyApp.exception.WorkerkNotFoundException;
import com.company.CompanyApp.security.dto.AuthenticationRequest;
import com.company.CompanyApp.security.dto.VerificationRequest;
import com.company.CompanyApp.security.service.IAuthenticationService;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import org.springframework.ui.Model;

/**
 * This controller deals with authentication requests.
 */
@Controller
@RequestMapping("/auth")
public class AuthController {
    private final IAuthenticationService authenticationService;
    private AuthenticationRequest registerInfo;


    //CONSTRUCTORS
    public AuthController(IAuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }


    /*
    @InitBinder indicates that this method should be called before the data
    binding process.

    WebDataBinder is a like a filter bean for the model objects.

    Here we specify, that we want all string fields to be trimmed.
    */
    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor trimmer = new StringTrimmerEditor(true);

        dataBinder.registerCustomEditor(String.class, trimmer);
    }


    /**
     * Show the login page to the user.
     */
    @GetMapping("/login")
    public String showLoginPage(Model model) {
        model.addAttribute("login", new AuthenticationRequest());

        return "auth/login-page";
    }


    /**
     * Show the registration page to the user.
     */
    @GetMapping("/register")
    public String showRegistrationPage(Model model) {
        model.addAttribute("register", new AuthenticationRequest());

        return "auth/register-page";
    }


    /**
     * Try to authenticate the user based on the retrieved data.
     */
    @PostMapping("/process-login")
    public String login(@Valid
                        @ModelAttribute("login")
                        AuthenticationRequest login,
                        BindingResult result) {

        if (result.hasErrors()) return "auth/login-page";

        try {
            authenticationService.authenticate(login);
            return "home-page";
        } catch (BadLoginInputException ex) {
            login.setError(ex.getMessage());
            return "auth/login-page";
        }
    }


    /**
     * Try to authenticate the user based on the retrieved data.
     */
    @PostMapping("/process-register")
    public String registration(@Valid
                               @ModelAttribute("register")
                               AuthenticationRequest register,
                               BindingResult result,
                               Model model) {

        if (result.hasErrors()) return "auth/register-page";

        try {
            registerInfo = register;
            String code = authenticationService.getValidationCode(register.getId());

            model.addAttribute("verify", new VerificationRequest());
            VerificationRequest.setCode(code);

            return "auth/verification-page";
        } catch(WorkerkNotFoundException ex) {
            register.setError(ex.getMessage());
            return "auth/register-page";
        }
    }


    /**
     *
     */
    @PostMapping("/verify")
    public String verification(@Valid
                               @ModelAttribute("verify")
                               VerificationRequest verify,
                               BindingResult result) {

        if (result.hasErrors()) return "auth/verification-page";

        authenticationService.register(registerInfo);

        return "home-page";
    }
}