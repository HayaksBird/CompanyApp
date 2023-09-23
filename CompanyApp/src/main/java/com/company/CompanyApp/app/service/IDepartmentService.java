package com.company.CompanyApp.app.service;

import com.company.CompanyApp.app.entity.Department;
import com.company.CompanyApp.exception.DepartmentNotFoundException;

public interface IDepartmentService {

    Department getDepartmentWithWorkers(int departmentId) throws DepartmentNotFoundException;

    Department getDepartment(int departmentId) throws DepartmentNotFoundException;
}
