package com.company.CompanyApp.app.dao;

import com.company.CompanyApp.app.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {

}
