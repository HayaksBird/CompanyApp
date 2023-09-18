package com.company.CompanyApp.entity.worker;

import com.company.CompanyApp.annotations.ViewName;
import com.company.CompanyApp.enums.WorkerType;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "data_analyst")
public class DataAnalyst extends Worker {
    @ViewName(message = "Database Tied To")
    @Column(name = "database_")
    private String database;

    //Don't want to show this at the GUI app
    @Column(name = "position_type")
    private String positionType;


    //CONSTRUCTORS
    public DataAnalyst() {}


    public DataAnalyst(int id,
                       String firstName,
                       String lastName,
                       int departmentId,
                       String email,
                       LocalDate employedSince,
                       LocalDate vacation,
                       BigDecimal salary,
                       WorkerType workerType,
                       String position,
                       String database,
                       String positionType) {

        super(id, firstName, lastName, departmentId, email, employedSince, vacation, salary, workerType, position);

        this.database = database;
        this.positionType = positionType;
    }


    //SETTERS & GETTERS
    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getPositionType() {
        return positionType;
    }

    public void setPositionType(String positionType) {
        this.positionType = positionType;
    }
}
