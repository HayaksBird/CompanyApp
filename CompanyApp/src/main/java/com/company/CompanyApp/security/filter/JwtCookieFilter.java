package com.company.CompanyApp.security.filter;

import com.company.CompanyApp.security.service.IJwtService;
import jakarta.servlet.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class JwtCookieFilter extends HttpFilter {
    private final IJwtService jwtService;

    private final long cookieLifespan;


    //CONSTRUCTORS
    public JwtCookieFilter(IJwtService jwtService,
                           long cookieLifespan) {
        this.jwtService = jwtService;
        this.cookieLifespan = cookieLifespan;
    }


    /*
    Filter chain works in a quite recursive way.

    The doFilter() method acts recursively. When the filterChain.doFilter(request, response)
    call is made, it continues down the chain to the next filter, and eventually, it
    reaches the servlet. Once the servlet has processed the request and sends back the
    response, the control starts moving back up the filter chain, with each filter's
    doFilter() method resuming where it was left off.
     */
    /**
     *
     */
    @Override
    protected void doFilter(HttpServletRequest request,
                            HttpServletResponse response,
                            FilterChain chain) throws IOException, ServletException {

        chain.doFilter(request, response);  //Make sure that the filter logic is triggered after the servlet is done

        if (response.getStatus() == 201 || response.getStatus() == 200) {
            String jwt;
            Cookie jwtCookie;

            Authentication userAuth = SecurityContextHolder.getContext().getAuthentication();

            jwt = getJwt(userAuth);

            jwtCookie = new Cookie("jwt", jwt);
            jwtCookie.setMaxAge((int) cookieLifespan / 1000);  //Pass in seconds
            jwtCookie.setDomain("localhost");
            jwtCookie.setPath("/");
            jwtCookie.setHttpOnly(true);

            response.addCookie(jwtCookie);
        }
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
}
