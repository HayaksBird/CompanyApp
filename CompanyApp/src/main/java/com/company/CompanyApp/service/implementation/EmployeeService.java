package com.company.CompanyApp.service.implementation;

import com.company.CompanyApp.dao.EmployeeRepository;
import com.company.CompanyApp.entity.Employee;
import com.company.CompanyApp.service.IEmployeeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService implements IEmployeeService {
    private final EmployeeRepository employeeRepository;


    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }


    @Override
    public Employee getEmployee(int id) {
        var employer = employeeRepository.findById(id);

        if (employer.isPresent()) return employer.get();
        else return null;
    }


    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }


    @Override
    public void addEmployee(Employee employee) {
        employeeRepository.save(employee);
    }


    @Override
    public void updateEmployee(Employee employee) {
        if (employeeRepository.findById(employee.getId()).isPresent()) {
            employeeRepository.save(employee);
        }
    }


    @Override
    public void deleteEmployee(int id) {
        var employer = employeeRepository.findById(id);

        if (employer.isPresent()) employeeRepository.delete(employer.get());
    }


    @Override
    public void deleteAllEmployees() {
        employeeRepository.deleteAll();
    }
}
