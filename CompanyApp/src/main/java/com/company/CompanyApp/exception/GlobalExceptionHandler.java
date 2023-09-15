package com.company.CompanyApp.exception;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthenticationException.class)
    public void handleException(HttpServletResponse response) throws IOException {
        response.sendRedirect("/auth/login");
    }


    @ExceptionHandler(IllegalAccessException.class)
    public void handleException(IllegalAccessException exc) {
        System.out.println("");
    }


    @ExceptionHandler()
    public void handleException(Exception exc) {
        System.out.println("");
    }
}
