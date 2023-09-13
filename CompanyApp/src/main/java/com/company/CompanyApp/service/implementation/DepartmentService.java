package com.company.CompanyApp.service.implementation;


import com.company.CompanyApp.dao.DepartmentRepository;
import com.company.CompanyApp.entity.Department;
import com.company.CompanyApp.exception.WorkerkNotFoundException;
import com.company.CompanyApp.service.IDepartmentService;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

@Service
public class DepartmentService implements IDepartmentService {
    private final DepartmentRepository departmentRepository;


    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }


    @Override
    public Department getDepartment(int departmentId) {
        var department = departmentRepository.findById(departmentId);

        if (department.isPresent()) {
            Hibernate.initialize(department.get().getWorkers());
            return department.get();
        } else throw new WorkerkNotFoundException(departmentId);
    }
}
