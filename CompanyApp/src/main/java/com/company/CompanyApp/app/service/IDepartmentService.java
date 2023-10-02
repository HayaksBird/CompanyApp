package com.company.CompanyApp.app.service;

import com.company.CompanyApp.app.entity.worker.Department;
import com.company.CompanyApp.exception.ItemNotFoundException;

public interface IDepartmentService {

    Department getDepartmentWithWorkers(int departmentId) throws ItemNotFoundException;

    Department getDepartment(int departmentId) throws ItemNotFoundException;

    void save(Department department);

    void delete(Department department);
}
