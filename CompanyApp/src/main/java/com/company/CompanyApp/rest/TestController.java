package com.company.CompanyApp.rest;

import com.company.CompanyApp.dao.DepartmentRepository;
import com.company.CompanyApp.entity.Department;
import com.company.CompanyApp.entity.Employee;
import com.company.CompanyApp.entity.Manager;
import com.company.CompanyApp.service.IEmployeeService;
import com.company.CompanyApp.service.IManagerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {
    private final IEmployeeService employeeService;

    private final IManagerService managerService;

    private final DepartmentRepository departmentRepository;


    public TestController(IEmployeeService employeeService,
                          IManagerService managerService,
                          DepartmentRepository departmentRepository) {

        this.departmentRepository = departmentRepository;
        this.employeeService = employeeService;
        this.managerService = managerService;
    }


    @GetMapping("/employees")
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }


    @DeleteMapping("/employees/{id}")
    public void DeleteEmployee(@PathVariable int id) {
        employeeService.deleteEmployee(id);
    }


    @PostMapping("/employees")
    public void addEmployee(@RequestBody Employee employee) {
        employeeService.addEmployee(employee);
    }


    @PutMapping("/employees")
    public void updateEmployee(@RequestBody Employee employee) {
        employeeService.updateEmployee(employee);
    }


    @GetMapping("/managers")
    public List<Manager> getAllManagers() {
        return managerService.getAllManagers();
    }


    @GetMapping("/departments/{id}")
    public Department getDepartment(@PathVariable int id) {
        var department = departmentRepository.findById(id);

        if (department.isPresent()) return department.get();
        else return null;
    }
}
