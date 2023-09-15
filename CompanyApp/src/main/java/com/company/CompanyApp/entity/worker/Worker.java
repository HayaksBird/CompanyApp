package com.company.CompanyApp.entity.worker;

import com.company.CompanyApp.entity.annotations.ViewName;
import com.company.CompanyApp.enums.WorkerType;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "worker")
public class Worker {
    @ViewName(message = "ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ViewName(message = "first name")
    @Column(name = "first_name")
    private String firstName;

    @ViewName(message = "last name")
    @Column(name = "last_name")
    private String lastName;

    @ViewName(message = "department id")
    @Column(name = "department_id")
    private int departmentId;

    @ViewName(message = "email")
    @Column(name = "email")
    private String email;

    @ViewName(message = "employed since")
    @Column(name = "employed_since")
    private Date employedSince;

    @ViewName(message = "next vacation")
    @Column(name = "vacation")
    private Date vacation;

    @ViewName(message = "salary")
    @Column(name = "salary")
    private BigDecimal salary;

    @ViewName(message = "worker type")
    @Enumerated(EnumType.STRING)
    @Column(name = "worker_type")
    private WorkerType workerType;

    @ViewName(message = "official position")
    @Column(name = "position")
    private String position;


    //CONSTRUCTORS
    public Worker() {}


    public Worker(int id,
                  String firstName,
                  String lastName,
                  int departmentId,
                  String email,
                  Date employedSince,
                  Date vacation,
                  BigDecimal salary,
                  WorkerType workerType,
                  String position) {

        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.departmentId = departmentId;
        this.email = email;
        this.employedSince = employedSince;
        this.vacation = vacation;
        this.salary = salary;
        this.workerType = workerType;
        this.position = position;
    }


    @Override
    public String toString() {
        return String.format("ID: <strong>%s</strong>&emsp;" +
                        "first name: <strong>%s</strong>&emsp;" +
                        "last name: <strong>%s</strong>&emsp;" +
                        "worker type: %s&emsp;" +
                        "position: %s",
                        id, firstName, lastName, workerType, position);
    }


    //Getters & Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
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

    public WorkerType getWorkerType() {
        return workerType;
    }

    public void setWorkerType(WorkerType workerType) {
        this.workerType = workerType;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
