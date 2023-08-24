package com.company.CompanyApp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "manager")
public class Manager {
    @Id
    @Column(name = "department_id")
    private int departmentId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "employed_since")
    private Date employedSince;

    @Column(name = "vacation")
    private Date vacation;

    @Column(name = "salary")
    private BigDecimal salary;

    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "department_id",
            referencedColumnName = "id",
            insertable = false,
            updatable = false)
    private Department department;


    //CONSTRUCTORS
    public Manager() {}


    public Manager(int departmentId,
                   String firstName,
                   String lastName,
                   String email,
                   Date employedSince,
                   Date vacation,
                   BigDecimal salary) {

        this.departmentId = departmentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.employedSince = employedSince;
        this.vacation = vacation;
        this.salary = salary;
    }

    //Setters & Getters
    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getEmployedSince() {
        return employedSince;
    }

    public void setEmployedSince(Date employedSince) {
        this.employedSince = employedSince;
    }

    public Date getVacation() {
        return vacation;
    }

    public void setVacation(Date vacation) {
        this.vacation = vacation;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
