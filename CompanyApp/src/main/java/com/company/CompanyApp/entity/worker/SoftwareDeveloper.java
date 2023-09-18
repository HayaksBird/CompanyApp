package com.company.CompanyApp.entity.worker;

import com.company.CompanyApp.annotations.ViewName;
import com.company.CompanyApp.enums.WorkerType;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "software_developer")
public class SoftwareDeveloper extends Worker {
    @ViewName(message = "Project")
    @Column(name = "project")
    private String project;

    //Don't want to show this at the GUI app
    @Column(name = "position_type")
    private String positionType;

    @ViewName(message = "Programming Language")
    @Column(name = "programming_language")
    private String programmingLanguage;

    @ViewName(message = "Field")
    @Column(name = "field")
    private String field;


    //CONSTRUCTORS
    public SoftwareDeveloper() {}


    public SoftwareDeveloper(int id,
                             String firstName,
                             String lastName,
                             int departmentId,
                             String email,
                             LocalDate employedSince,
                             LocalDate vacation,
                             BigDecimal salary,
                             WorkerType workerType,
                             String position,
                             String project,
                             String positionType,
                             String programmingLanguage,
                             String field) {

        super(id, firstName, lastName, departmentId, email, employedSince, vacation, salary, workerType, position);

        this.project = project;
        this.positionType = positionType;
        this.programmingLanguage = programmingLanguage;
        this.field = field;
    }


    //SETTERS & GETTERS
    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getPositionType() {
        return positionType;
    }

    public void setPositionType(String positionType) {
        this.positionType = positionType;
    }

    public String getProgrammingLanguage() {
        return programmingLanguage;
    }

    public void setProgrammingLanguage(String programmingLanguage) {
        this.programmingLanguage = programmingLanguage;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
}
