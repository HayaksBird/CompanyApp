package com.company.CompanyApp.controller;

import com.company.CompanyApp.security.dto.AuthenticationRequest;
import com.company.CompanyApp.security.service.IAuthenticationService;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
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
    @GetMapping("")
    public String showLoginPage(Model model) {
        model.addAttribute("request", new AuthenticationRequest());

        return "login-page";
    }


    /**
     * Try to authenticate the user based on the retrieved data.
     */
    @PostMapping("/login")
    public void login(@Valid
                      @ModelAttribute("customer")
                      AuthenticationRequest request) {

        authenticationService.authenticate(request);
    }
}
