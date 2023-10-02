package com.company.CompanyApp.app.entity.worker;

import com.company.CompanyApp.validation.annotations.ViewName;
import com.company.CompanyApp.validation.annotations.CanBeNull;
import com.company.CompanyApp.validation.annotations.PositionType;
import jakarta.persistence.*;

@Entity
@Table(name = "data_analyst")
public class DataAnalyst extends Worker {
    @CanBeNull
    @ViewName(message = "Database Tied To")
    @Column(name = "database_")
    private String database;

    @PositionType(types = {"JUNIOR", "SENIOR"})
    @ViewName(message = "Position Type")
    @Column(name = "position_type")
    private String positionType;


    //CONSTRUCTORS
    public DataAnalyst() {}


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
