package com.company.CompanyApp.dao;

import com.company.CompanyApp.entity.Employer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployerRepository extends JpaRepository<Employer, Integer> {
}
