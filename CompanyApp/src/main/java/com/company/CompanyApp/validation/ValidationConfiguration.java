package com.company.CompanyApp.validation;

import jakarta.validation.Validator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/**
 * The configuration file for the validation layer.
 */
@Configuration
public class ValidationConfiguration {

    /**
     * Create a special validator factory, which will allow all validators to be beans
     * that will be managed my the spring container. This will allow us to inject services
     * into these validators.
     */
    @Bean
    public Validator localValidatorFactoryBean() {
        return new LocalValidatorFactoryBean();
    }

}
