package com.company.CompanyApp.app.service.implementation;


import com.company.CompanyApp.app.dao.DepartmentRepository;
import com.company.CompanyApp.app.entity.Department;
import com.company.CompanyApp.app.service.IDepartmentService;
import com.company.CompanyApp.exception.ItemNotFoundException;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

@Service
public class DepartmentService implements IDepartmentService {
    private final DepartmentRepository departmentRepository;


    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }


    @Override
    public Department getDepartmentWithWorkers(int departmentId) throws ItemNotFoundException {
        var department = departmentRepository.findById(departmentId);

        if (department.isPresent()) {
            Hibernate.initialize(department.get().getWorkers());
            return department.get();
        } else throw new ItemNotFoundException(Department.class, departmentId);
    }


    @Override
    public Department getDepartment(int departmentId) throws ItemNotFoundException {
        var department = departmentRepository.findById(departmentId);

        if (department.isPresent()) return department.get();
        else throw new ItemNotFoundException(Department.class, departmentId);
    }


    @Override
    public void save(Department department) {
        departmentRepository.save(department);
    }


    @Override
    public void delete(Department department) {
        departmentRepository.delete(department);
    }
}
