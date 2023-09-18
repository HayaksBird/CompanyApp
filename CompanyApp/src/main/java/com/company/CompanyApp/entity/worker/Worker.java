package com.company.CompanyApp.entity.worker;

import com.company.CompanyApp.annotations.ViewName;
import com.company.CompanyApp.enums.WorkerType;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "worker")
public class Worker {
    @ViewName(message = "ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ViewName(message = "First Name")
    @Column(name = "first_name")
    private String firstName;

    @ViewName(message = "Last Name")
    @Column(name = "last_name")
    private String lastName;

    @ViewName(message = "Department ID")
    @Column(name = "department_id")
    private int departmentId;

    @ViewName(message = "Email")
    @Column(name = "email")
    private String email;

    @ViewName(message = "Employed Since")
    @Column(name = "employed_since")
    private LocalDate employedSince;

    @ViewName(message = "Next Vacation")
    @Column(name = "vacation")
    private LocalDate vacation;

    @ViewName(message = "Salary")
    @Column(name = "salary")
    private BigDecimal salary;

    //Don't want to show this at the GUI app
    @Enumerated(EnumType.STRING)
    @Column(name = "worker_type")
    private WorkerType workerType;

    @ViewName(message = "Official Position")
    @Column(name = "position")
    private String position;


    //CONSTRUCTORS
    public Worker() {}


    public Worker(int id,
                  String firstName,
                  String lastName,
                  int departmentId,
                  String email,
                  LocalDate employedSince,
                  LocalDate vacation,
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
                        "position: %s",
                        id, firstName, lastName, position);
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

    public LocalDate getEmployedSince() {
        return employedSince;
    }

    public void setEmployedSince(LocalDate  employedSince) {
        this.employedSince = employedSince;
    }

    public LocalDate getVacation() {
        return vacation;
    }

    public void setVacation(LocalDate vacation) {
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
