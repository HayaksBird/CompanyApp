package com.company.CompanyApp.entity.worker;

import com.company.CompanyApp.annotations.ViewName;
import com.company.CompanyApp.enums.WorkerType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "intern")
public class Intern extends Worker {
    @ViewName(message = "University")
    @Column(name = "university")
    private String university;

    @ViewName(message = "Employed Until")
    @Column(name = "employed_until")
    private LocalDate employedUntil;

    @ViewName(message = "Is University Insurance Present")
    @Column(name = "has_university_insurance")
    private boolean hasUniversityInsurance;


    //CONSTRUCTORS
    public Intern() {}


    public Intern(int id,
                  String firstName,
                  String lastName,
                  int departmentId,
                  String email,
                  LocalDate employedSince,
                  LocalDate vacation,
                  BigDecimal salary,
                  WorkerType workerType,
                  String position,
                  String university,
                  LocalDate employedUntil,
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

    public LocalDate getEmployedUntil() {
        return employedUntil;
    }

    public void setEmployedUntil(LocalDate employedUntil) {
        this.employedUntil = employedUntil;
    }

    public boolean isHasUniversityInsurance() {
        return hasUniversityInsurance;
    }

    public void setHasUniversityInsurance(boolean hasUniversityInsurance) {
        this.hasUniversityInsurance = hasUniversityInsurance;
    }
}
