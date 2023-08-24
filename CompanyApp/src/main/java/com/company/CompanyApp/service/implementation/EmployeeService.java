package com.company.CompanyApp.service.implementation;

import com.company.CompanyApp.dao.EmployeeRepository;
import com.company.CompanyApp.entity.Employee;
import com.company.CompanyApp.exception.WorkerkNotFoundException;
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
        var employee = employeeRepository.findById(id);

        if (employee.isPresent()) return employee.get();
        else throw new WorkerkNotFoundException(id);
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
        var employee = employeeRepository.findById(id);

        if (employee.isPresent()) employeeRepository.delete(employee.get());
        else throw new WorkerkNotFoundException(id);
    }


    @Override
    public void deleteAllEmployees() {
        employeeRepository.deleteAll();
    }
}
