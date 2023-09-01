package com.company.CompanyApp.security;

import com.company.CompanyApp.enums.WorkerType;
import com.company.CompanyApp.security.filter.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * This class provides the security configuration.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final MyAuthenticationEntryPoint authEntryPoint;


    //CONSTRUCTORS
    public SecurityConfig(AuthenticationProvider authenticationProvider,
                          JwtAuthenticationFilter jwtAuthFilter,
                          MyAuthenticationEntryPoint authEntryPoint) {

        this.authenticationProvider = authenticationProvider;
        this.jwtAuthFilter = jwtAuthFilter;
        this.authEntryPoint = authEntryPoint;
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
                                .requestMatchers("/css/**").permitAll()
                                //Regarding home page
                                .requestMatchers(HttpMethod.GET,"/home")
                                    .hasRole(WorkerType.EMPLOYEE.name())
                                .requestMatchers(HttpMethod.GET,"/home/**")
                                    .hasRole(WorkerType.EMPLOYEE.name())
        );

        http.csrf(csrf -> csrf.disable());
        return  http.authenticationProvider(authenticationProvider)
                    .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                    //Specify the auth entry point to use, when an auth exception is met
                    .exceptionHandling(
                        exceptionHandling -> exceptionHandling
                            .authenticationEntryPoint(authEntryPoint)
                    )
                    .build();
    }
}
