package com.company.CompanyApp.entity.user;

import com.company.CompanyApp.enums.RoleType;
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
    private RoleType role;


    //CONSTRUCTORS
    public Role() {}


    public Role(RoleType role, int userId) {
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

    public RoleType getRole() {
        return role;
    }

    public void setRole(RoleType role) {
        this.role = role;
    }
}
