package com.company.CompanyApp.security.filter;

import com.company.CompanyApp.security.service.IJwtService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    private final IJwtService jwtService;


    public FilterConfig(IJwtService jwtService) {
        this.jwtService = jwtService;
    }


    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(jwtService);
    }
}
