package com.company.CompanyApp.service.implementation;

import com.company.CompanyApp.dao.EmployeeRepository;
import com.company.CompanyApp.dao.WorkerRepository;
import com.company.CompanyApp.entity.Employee;
import com.company.CompanyApp.entity.Worker;
import com.company.CompanyApp.exception.WorkerkNotFoundException;
import com.company.CompanyApp.service.IEmployeeService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService implements IEmployeeService {
    private final WorkerRepository employeeRepository;


    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }


    @Override
    public Employee getEmployee(int id) {
        var employee = employeeRepository.findById(id);

        if (employee.isPresent()) return (Employee)employee.get();
        else throw new WorkerkNotFoundException(id);
    }


    @Override
    public List<Employee> getAllEmployees() {
        return listToEmployee(employeeRepository.findAll());
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


    /**
     *
     */
    private List<Employee> listToEmployee(List<Worker> workers) {
        List<Employee> employees = new ArrayList<Employee>();

        for (Worker worker : workers) {
            employees.add((Employee)worker);
        }

        return employees;
    }
}
