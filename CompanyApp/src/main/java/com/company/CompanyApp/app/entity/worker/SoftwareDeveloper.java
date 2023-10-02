package com.company.CompanyApp.app.entity.worker;

import com.company.CompanyApp.validation.annotations.ViewName;
import com.company.CompanyApp.validation.annotations.CanBeNull;
import com.company.CompanyApp.validation.annotations.PositionType;
import jakarta.persistence.*;

@Entity
@Table(name = "software_developer")
public class SoftwareDeveloper extends Worker {
    @CanBeNull
    @ViewName(message = "Project")
    @Column(name = "project")
    private String project;

    @PositionType(types = {"JUNIOR", "SENIOR"})
    @ViewName(message = "Position Type")
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
