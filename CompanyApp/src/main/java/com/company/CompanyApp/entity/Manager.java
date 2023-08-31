package com.company.CompanyApp.entity;

import jakarta.persistence.*;


@Entity
@Table(name = "manager")
public class Manager extends Worker {
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
