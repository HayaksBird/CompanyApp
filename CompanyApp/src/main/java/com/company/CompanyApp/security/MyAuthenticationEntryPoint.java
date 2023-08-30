package com.company.CompanyApp.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

/**
 * This class will manage the exception caught during the auth process.
 * Note that the class does not handle the exception, it just redirects it
 * to the @ControllerAdvice class
 */
@Component
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final HandlerExceptionResolver resolver;


    public MyAuthenticationEntryPoint(@Qualifier("handlerExceptionResolver")
                                      HandlerExceptionResolver resolver) {

        this.resolver = resolver;
    }


    /**
    This method will pass the exception to the HandlerExceptionResolver bean,
    which in turn will list through all its possible resolvers, until it will
    use the controller advice bean to pass the exception.
     */
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        resolver.resolveException(request, response, null, authException);
    }
}
