package com.company.CompanyApp.app.entity.worker;

import com.company.CompanyApp.validation.annotations.ViewName;
import com.company.CompanyApp.validation.annotations.CanBeNull;
import jakarta.persistence.*;


@Entity
@Table(name = "manager")
public class Manager extends Worker {
    @CanBeNull
    @ViewName(message = "Progress Report")
    @Column(name = "progress_report")
    private String progressReport;


    //CONSTRUCTORS
    public Manager() {}


    //Getters & Setters
    public String getProgressReport() {
        return progressReport;
    }

    public void setProgressReport(String progressReport) {
        this.progressReport = progressReport;
    }
}
