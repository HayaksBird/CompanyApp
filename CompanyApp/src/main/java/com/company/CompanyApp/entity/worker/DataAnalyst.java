package com.company.CompanyApp.entity.worker;

import com.company.CompanyApp.enums.PositionType;
import com.company.CompanyApp.enums.WorkerType;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "data_analyst")
public class DataAnalyst extends Worker {
    @Column(name = "database_")
    private String database;

    @Enumerated(EnumType.STRING)
    @Column(name = "position_type")
    private PositionType positionType;


    //CONSTRUCTORS
    public DataAnalyst() {}


    public DataAnalyst(int id,
                       String firstName,
                       String lastName,
                       int departmentId,
                       String email,
                       Date employedSince,
                       Date vacation,
                       BigDecimal salary,
                       WorkerType workerType,
                       String position,
                       String database,
                       PositionType positionType) {

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

    public PositionType getPositionType() {
        return positionType;
    }

    public void setPositionType(PositionType positionType) {
        this.positionType = positionType;
    }
}
