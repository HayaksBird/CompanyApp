package com.company.CompanyApp.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "department")
public class Department {
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "employee_count")
    private int employeeCount;

    @Column(name = "min_budget")
    private int minBudget;

    @OneToMany
    @JoinColumn(name = "department_id",
                referencedColumnName = "id",
                insertable = false,
                updatable = false)
    private List<Employee> employees;

    @OneToOne
    @JoinColumn(name = "id",
            referencedColumnName = "department_id",
            insertable = false,
            updatable = false)
    private Manager manager;


    //CONSTRUCTORS
    public Department() {}


    //Setters & Getters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEmployeeCount() {
        return employeeCount;
    }

    public void setEmployeeCount(int employeeCount) {
        this.employeeCount = employeeCount;
    }

    public int getMinBudget() {
        return minBudget;
    }

    public void setMinBudget(int minBudget) {
        this.minBudget = minBudget;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }
}
