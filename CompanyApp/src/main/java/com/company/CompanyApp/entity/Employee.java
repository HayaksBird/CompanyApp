package com.company.CompanyApp.entity;

import jakarta.persistence.*;


@Entity
@Table(name = "employee")
public class Employee extends Worker {
    @Column(name = "position")
    private String position;


    //CONSTRUCTORS
    public Employee() {}


    //Getters & Setters
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
