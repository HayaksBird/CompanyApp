package com.company.CompanyApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class CompanyAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(CompanyAppApplication.class, args);
	}

}
