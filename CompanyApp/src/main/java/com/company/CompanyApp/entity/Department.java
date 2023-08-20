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

    @Column(name = "employer_count")
    private int employerCount;

    @Column(name = "min_budget")
    private int minBudget;

    @OneToMany
    @JoinColumn(name = "department_id",
                referencedColumnName = "id",
                insertable = false,
                updatable = false)
    private List<Employer> employers;

    @OneToOne
    @JoinColumn(name = "manager_id",
            referencedColumnName = "id",
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

    public int getEmployerCount() {
        return employerCount;
    }

    public void setEmployerCount(int employerCount) {
        this.employerCount = employerCount;
    }

    public int getMinBudget() {
        return minBudget;
    }

    public void setMinBudget(int minBudget) {
        this.minBudget = minBudget;
    }

    public List<Employer> getEmployers() {
        return employers;
    }

    public void setEmployers(List<Employer> employers) {
        this.employers = employers;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }
}
