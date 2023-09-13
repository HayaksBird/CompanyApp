package com.company.CompanyApp.entity.worker;

import com.company.CompanyApp.enums.WorkerType;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


@Entity
@Table(name = "manager")
public class Manager extends Worker {
    @Column(name = "progress_report")
    private String progressReport;


    //CONSTRUCTORS
    public Manager() {}


    public Manager(int id,
                   String firstName,
                   String lastName,
                   int departmentId,
                   String email,
                   Date employedSince,
                   Date vacation,
                   BigDecimal salary,
                   WorkerType workerType,
                   String position,
                   String progressReport) {

        super(id, firstName, lastName, departmentId, email, employedSince, vacation, salary, workerType, position);

        this.progressReport = progressReport;
    }


    //Getters & Setters
    public String getProgressReport() {
        return progressReport;
    }

    public void setProgressReport(String progressReport) {
        this.progressReport = progressReport;
    }
}
