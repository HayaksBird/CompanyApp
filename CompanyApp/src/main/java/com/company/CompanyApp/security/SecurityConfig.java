package com.company.CompanyApp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.web.SecurityFilterChain;

/**
 * This class provides the security configuration.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final AuthenticationProvider authenticationProvider;


    //CONSTRUCTORS
    public SecurityConfig(AuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
    }


    /**
     * This method is responsible for securing the endpoints with the role requirements.
     * TO BE IMPLEMENTED:
     * In addition to that, it embeds the custom JWT filter to the chain for the authentication.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
                configurer ->
                        configurer
                                //Regarding authentication
                                .requestMatchers("/auth").permitAll()
                                .requestMatchers("/auth/**").permitAll()

                                //Regarding students
        );

        http.csrf(csrf -> csrf.disable());
        return  http.authenticationProvider(authenticationProvider).build();
    }
}
