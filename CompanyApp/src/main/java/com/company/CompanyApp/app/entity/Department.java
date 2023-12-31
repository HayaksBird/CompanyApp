package com.company.CompanyApp.app.entity;

import com.company.CompanyApp.app.entity.worker.Worker;
import com.company.CompanyApp.validation.annotations.NoNullEntity;
import com.company.CompanyApp.validation.annotations.ViewName;
import jakarta.persistence.*;

import java.util.List;

@NoNullEntity
@Entity
@Table(name = "department")
public class Department {
    @Id
    @Column(name = "id")
    private int id;

    @ViewName(message = "Name")
    @Column(name = "name")
    private String name;

    @Column(name = "employee_count")
    private int employeeCount;

    @Column(name = "min_budget")
    private int minBudget;

    /**
     * If the department is deleted, then delete all of its workers.
     */
    @OneToMany(cascade = CascadeType.REMOVE,
               fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id",
                referencedColumnName = "id")
    private List<Worker> workers;


    //CONSTRUCTORS
    public Department() {}


    //Getters & Setters
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

    public List<Worker> getWorkers() {
        return workers;
    }

    public void setWorkers(List<Worker> workers) {
        this.workers = workers;
    }
}