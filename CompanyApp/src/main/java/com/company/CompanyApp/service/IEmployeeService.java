package com.company.CompanyApp.service;

import com.company.CompanyApp.entity.Employee;

import java.util.List;

public interface IEmployeeService {
    Employee getEmployee(int id);


    List<Employee> getAllEmployees();


    void addEmployee(Employee employee);


    void updateEmployee(Employee employee);


    void deleteEmployee(int id);


    void deleteAllEmployees();
}
