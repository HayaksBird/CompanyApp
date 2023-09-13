package com.company.CompanyApp.entity.worker;

import com.company.CompanyApp.enums.PositionType;
import com.company.CompanyApp.enums.WorkerType;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "software_developer")
public class SoftwareDeveloper extends Worker {
    @Column(name = "project")
    private String project;

    @Enumerated(EnumType.STRING)
    @Column(name = "position_type")
    private PositionType positionType;

    @Column(name = "programming_language")
    private String programmingLanguage;

    @Column(name = "field")
    private String field;


    //CONSTRUCTORS
    public SoftwareDeveloper() {}


    public SoftwareDeveloper(int id,
                             String firstName,
                             String lastName,
                             int departmentId,
                             String email,
                             Date employedSince,
                             Date vacation,
                             BigDecimal salary,
                             WorkerType workerType,
                             String position,
                             String project,
                             PositionType positionType,
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

    public PositionType getPositionType() {
        return positionType;
    }

    public void setPositionType(PositionType positionType) {
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
