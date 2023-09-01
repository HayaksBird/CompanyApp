package com.company.CompanyApp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.*;

@Entity
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "user_id")
    private int userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private WorkerType role;


    //CONSTRUCTORS
    public Role() {}


    public Role(WorkerType role, int userId) {
        this.role = role;
        this.userId = userId;
    }


    //Setters & Getters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public WorkerType getRole() {
        return role;
    }

    public void setRole(WorkerType role) {
        this.role = role;
    }
}
