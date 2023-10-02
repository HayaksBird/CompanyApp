package com.company.CompanyApp.app.entity.worker;

import com.company.CompanyApp.validation.annotations.ViewName;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Future;

import java.time.LocalDate;

@Entity
@Table(name = "intern")
public class Intern extends Worker {
    @ViewName(message = "University")
    @Column(name = "university")
    private String university;

    @Future(message = "Invalid employment period date!")
    @ViewName(message = "Employed Until")
    @Column(name = "employed_until")
    private LocalDate employedUntil;

    @ViewName(message = "Is University Insurance Present")
    @Column(name = "has_university_insurance")
    private boolean hasUniversityInsurance;


    //CONSTRUCTORS
    public Intern() {}


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
