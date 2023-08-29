package com.company.CompanyApp.security.service;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.GrantedAuthority;

import java.util.Map;
import java.util.function.Function;
import java.util.List;

public interface IJwtService {
    /**
     * Generate a token with the extra claims.
     */
    <T> String generateToken(Map<String, Object> extraClaims,
                             Function<T, String> usernameExtractor,
                             T usernameContainer);


    /**
     * Generate a token without the extra claims.
     */
    <T> String generateToken(Function<T, String> usernameExtractor,
                             T usernameContainer);



    /**
     * This method returns the username claim for the JWT.
     */
    String extractUsername(String token);


    /**
     * This method returns the roles claim for the JWT.
     */
    List<? extends GrantedAuthority> extractRoles(String token);


    /**
     * This method is responsible for extracting any claim from the JWT.
     */
    <T> T extractClaim(String token,
                       Function<Claims, T> claimsResolver);


    long getLifespan();
}
