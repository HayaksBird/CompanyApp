package com.company.CompanyApp.entity.worker;

import com.company.CompanyApp.enums.WorkerType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "intern")
public class Intern extends Worker {
    @Column(name = "university")
    private String university;

    @Column(name = "employed_until")
    private Date employedUntil;

    @Column(name = "has_university_insurance")
    private boolean hasUniversityInsurance;


    //CONSTRUCTORS
    public Intern() {}


    public Intern(int id,
                  String firstName,
                  String lastName,
                  int departmentId,
                  String email,
                  Date employedSince,
                  Date vacation,
                  BigDecimal salary,
                  WorkerType workerType,
                  String position,
                  String university,
                  Date employedUntil,
                  boolean hasUniversityInsurance) {

        super(id, firstName, lastName, departmentId, email, employedSince, vacation, salary, workerType, position);

        this.university = university;
        this.employedUntil = employedUntil;
        this.hasUniversityInsurance = hasUniversityInsurance;
    }


    //SETTERS & GETTERS
    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public Date getEmployedUntil() {
        return employedUntil;
    }

    public void setEmployedUntil(Date employedUntil) {
        this.employedUntil = employedUntil;
    }

    public boolean isHasUniversityInsurance() {
        return hasUniversityInsurance;
    }

    public void setHasUniversityInsurance(boolean hasUniversityInsurance) {
        this.hasUniversityInsurance = hasUniversityInsurance;
    }
}
