package com.company.CompanyApp.security.controller;

import com.company.CompanyApp.security.dto.AuthenticationRequest;
import com.company.CompanyApp.security.dto.VerificationRequest;
import com.company.CompanyApp.security.service.IAuthenticationService;
import com.company.CompanyApp.validation.service.ValidationService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import org.springframework.ui.Model;

import java.io.IOException;
import java.util.List;

/**
 * This controller deals with authentication requests.
 */
@Controller
@RequestMapping("/auth")
public class AuthController {
    private final IAuthenticationService authenticationService;
    private final ValidationService validationService;
    private final String templateDir;
    private AuthenticationRequest registerInfo;


    //CONSTRUCTORS
    public AuthController(IAuthenticationService authenticationService,
                          ValidationService validationService) {

        templateDir = "auth";
        this.validationService = validationService;
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

        return String.format("%s/login-page", templateDir);
    }


    /**
     * Show the registration page to the user.
     */
    @GetMapping("/register")
    public String showRegistrationPage(Model model) {
        model.addAttribute("register", new AuthenticationRequest());

        return String.format("%s/register-page", templateDir);
    }


    /**
     * Try to authenticate the user based on the retrieved data.
     */
    @PostMapping("/process-login")
    public String login(@ModelAttribute("login")
                        AuthenticationRequest login,
                        HttpServletResponse response) throws IOException {

        List<String> errorMessages;

        errorMessages = validationService.validate(login);

        if (errorMessages.isEmpty()) {
            String badLogin = authenticationService.authenticate(login);

            if (badLogin != null) errorMessages.add(badLogin);
        }

        if (errorMessages.isEmpty()) {
            response.addCookie(authenticationService.generateJwtCookie());
            response.sendRedirect("/home");

            return null;
        } else {
            login.setErrorMessages(errorMessages);

            return String.format("%s/login-page", templateDir);
        }
    }


    /**
     * Try to authenticate the user based on the retrieved data.
     */
    @PostMapping("/process-register")
    public String registration(@ModelAttribute("register")
                               AuthenticationRequest register,
                               Model model) {

        List<String> errorMessages;

        errorMessages = validationService.validate(register);

        if (errorMessages.isEmpty()) {
            String noWorker = authenticationService.sendValidationCode(register.getId());

            if (noWorker != null) errorMessages.add(noWorker);
        }

        if (errorMessages.isEmpty()) {
            registerInfo = register;
            model.addAttribute("verify", new VerificationRequest());

            return String.format("%s/verification-page", templateDir);
        } else {
            register.setErrorMessages(errorMessages);

            return String.format("%s/register-page", templateDir);
        }
    }


    /**
     *
     */
    @PostMapping("/verify")
    public String verification(@ModelAttribute("verify")
                               VerificationRequest verify,
                               HttpServletResponse response)
                               throws IOException, NoSuchFieldException, ClassNotFoundException, IllegalAccessException {

        List<String> errorMessages;

        errorMessages = validationService.validate(verify);

        if (errorMessages.isEmpty()) {
            authenticationService.register(registerInfo);

            response.addCookie(authenticationService.generateJwtCookie());
            response.sendRedirect("/home");

            return null;
        } else {
            verify.setErrorMessages(errorMessages);

            return String.format("%s/verification-page", templateDir);
        }
    }
}