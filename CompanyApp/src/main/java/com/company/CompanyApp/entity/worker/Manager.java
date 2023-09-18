package com.company.CompanyApp.entity.worker;

import com.company.CompanyApp.annotations.ViewName;
import com.company.CompanyApp.enums.WorkerType;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;


@Entity
@Table(name = "manager")
public class Manager extends Worker {
    @ViewName(message = "Progress Report")
    @Column(name = "progress_report")
    private String progressReport;


    //CONSTRUCTORS
    public Manager() {}


    public Manager(int id,
                   String firstName,
                   String lastName,
                   int departmentId,
                   String email,
                   LocalDate employedSince,
                   LocalDate vacation,
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
