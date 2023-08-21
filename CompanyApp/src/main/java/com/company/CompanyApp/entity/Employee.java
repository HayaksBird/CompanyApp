package com.company.CompanyApp.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "department_id")
    private int departmentId;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "department_id",
                referencedColumnName = "id",
                insertable = false,
                updatable = false)
    private Department department;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "position")
    private String position;

    @Column(name = "employed_since")
    private Date employedSince;

    @Column(name = "vacation")
    private Date vacation;

    @Column(name = "salary")
    private BigDecimal salary;


    //CONSTRUCTORS
    public Employee() {}


    @JsonCreator
    public Employee(@JsonProperty("id") int id,
                    @JsonProperty("department_id") int departmentId,
                    @JsonProperty("first_name") String firstName,
                    @JsonProperty("last_name") String lastName,
                    @JsonProperty("position") String position,
                    @JsonProperty("employed_since") Date employedSince,
                    @JsonProperty("vacation") Date vacation,
                    @JsonProperty("salary") BigDecimal salary) {

        this.id = id;
        this.departmentId = departmentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.employedSince = employedSince;
        this.vacation = vacation;
        this.salary = salary;
    }

    //Setters & Getters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
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
}
