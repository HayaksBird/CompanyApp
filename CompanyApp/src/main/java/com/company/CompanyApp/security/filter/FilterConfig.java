package com.company.CompanyApp.security.filter;

import com.company.CompanyApp.security.service.IJwtService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    @Value("${application.security.jwt.expiration}")
    private long COOKIE_LIFESPAN;
    private final IJwtService jwtService;


    public FilterConfig(IJwtService jwtService) {
        this.jwtService = jwtService;
    }


    //@Bean
    public FilterRegistrationBean<JwtCookieFilter> jwtCookieFilter() {
        FilterRegistrationBean<JwtCookieFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new JwtCookieFilter(jwtService, COOKIE_LIFESPAN));
        registrationBean.addUrlPatterns("/auth/process-login", "/auth/verify");
        registrationBean.setOrder(1);

        return registrationBean;
    }


    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(jwtService);
    }
}
