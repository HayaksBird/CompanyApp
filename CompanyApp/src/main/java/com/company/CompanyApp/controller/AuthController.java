package com.company.CompanyApp.controller;

import com.company.CompanyApp.exception.BadLoginInputException;
import com.company.CompanyApp.exception.WorkerkNotFoundException;
import com.company.CompanyApp.security.dto.AuthenticationRequest;
import com.company.CompanyApp.security.dto.VerificationRequest;
import com.company.CompanyApp.security.service.IAuthenticationService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

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
    public ModelAndView login(@Valid
                              @ModelAttribute("login")
                              AuthenticationRequest login,
                              BindingResult result,
                              HttpServletResponse response) throws IOException {

        ModelAndView modelAndView = new ModelAndView();

        if (result.hasErrors()) {
            modelAndView.setViewName("auth/login-page");
            modelAndView.setStatus(HttpStatus.BAD_REQUEST);
        } else {
            try {
                authenticationService.authenticate(login);

                response.addCookie(authenticationService.generateJwtCookie());
                response.sendRedirect("/home");
                return null;
            } catch (BadLoginInputException ex) {
                login.setError(ex.getMessage());
                modelAndView.setViewName("auth/login-page");
                modelAndView.setStatus(HttpStatus.NOT_FOUND);
            }
        }

        return modelAndView;
    }


    /**
     * Try to authenticate the user based on the retrieved data.
     */
    @PostMapping("/process-register")
    public ModelAndView registration(@Valid
                                     @ModelAttribute("register")
                                     AuthenticationRequest register,
                                     BindingResult result,
                                     Model model) {

        ModelAndView modelAndView = new ModelAndView();

        if (result.hasErrors()) {
            modelAndView.setViewName("auth/register-page");
            modelAndView.setStatus(HttpStatus.BAD_REQUEST);
        } else {
            try {
                registerInfo = register;
                String code = authenticationService.getValidationCode(register.getId());

                model.addAttribute("verify", new VerificationRequest());
                VerificationRequest.setCode(code);

                modelAndView.setViewName("auth/verification-page");
                modelAndView.setStatus(HttpStatus.OK);
            } catch (WorkerkNotFoundException ex) {
                register.setError(ex.getMessage());

                modelAndView.setViewName("auth/register-page");
                modelAndView.setStatus(HttpStatus.NOT_FOUND);
            }
        }

        return modelAndView;
    }


    /**
     *
     */
    @PostMapping("/verify")
    public ModelAndView verification(@Valid
                                     @ModelAttribute("verify")
                                     VerificationRequest verify,
                                     BindingResult result,
                                     HttpServletResponse response) throws IOException {

        ModelAndView modelAndView = new ModelAndView();

        if (result.hasErrors()) {
            modelAndView.setViewName("auth/verification-page");
            modelAndView.setStatus(HttpStatus.BAD_REQUEST);
        } else {
            authenticationService.register(registerInfo);

            response.addCookie(authenticationService.generateJwtCookie());
            response.sendRedirect("/home");
            return null;
        }

        return modelAndView;
    }
}