package com.company.CompanyApp.security.filter;

import com.company.CompanyApp.entity.User;
import com.company.CompanyApp.security.service.IJwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.lang.NonNull;

import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final IJwtService jwtService;


    //CONSTRUCTORS
    public JwtAuthenticationFilter(IJwtService jwtService) {
        this.jwtService = jwtService;
    }


    /**
     * This is the filter method itself.
     * It validates the JWT, extracts the user & his roles and initializes the
     * SecurityContextHolder by providing the Authentication object.
     */
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        String jwt;
        String id;
        User userDetails;
        Cookie jwtCookie;

        jwtCookie = getCookie(request, "jwt");

        if (jwtCookie == null) {
            filterChain.doFilter(request, response);
            return;
        }

        jwt = jwtCookie.getValue();

        id = jwtService.extractUsername(jwt);

        if (id != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            userDetails = new User();
            userDetails.setId(Integer.parseInt(id));

            //Create the Authentication object.
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    jwtService.extractRoles(jwt)
            );

            SecurityContextHolder.getContext().setAuthentication(token);
        }

        //Call the next filter in the chain.
        filterChain.doFilter(request, response);
    }


    /**
     *
     */
    private Cookie getCookie (HttpServletRequest request, String name) {
        for (Cookie cookie : request.getCookies()) {
            System.out.println(cookie.getName() + " " + cookie.getValue());
            if (cookie.getName().equals(name)) return cookie;
        }

        return null;
    }
}
