package com.company.CompanyApp.security;

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
                                .requestMatchers(HttpMethod.GET,"/home").hasRole("D")

                                //Regarding department page
                                .requestMatchers(HttpMethod.GET,"/department").hasRole("D")
                                .requestMatchers(HttpMethod.GET,"/department/**").hasRole("A")
                                .requestMatchers(HttpMethod.POST,"/department/**").hasRole("A")
                                .requestMatchers(HttpMethod.DELETE,"/department/**").hasRole("A")
                                .requestMatchers(HttpMethod.PUT,"/department/**").hasRole("A")

                                //Regarding personal page
                                .requestMatchers(HttpMethod.GET,"/personal").hasRole("D")
                                .requestMatchers(HttpMethod.GET,"/personal/**").hasRole("B")
                                .requestMatchers(HttpMethod.POST,"/personal/**").hasRole("B")
                                .requestMatchers(HttpMethod.DELETE,"/personal/**").hasRole("B")
                                .requestMatchers(HttpMethod.PUT,"/personal/**").hasRole("B")

                                //Regarding posts
                                .requestMatchers(HttpMethod.GET,"/post/**").hasRole("C")
                                .requestMatchers(HttpMethod.POST,"/post/**").hasRole("C")
                                .requestMatchers(HttpMethod.DELETE,"/post/**").hasRole("C")
                                .requestMatchers(HttpMethod.PUT,"/post/**").hasRole("C")
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
