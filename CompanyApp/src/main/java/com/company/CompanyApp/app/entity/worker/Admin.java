package com.company.CompanyApp.app.entity.worker;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "admin")
public class Admin extends Worker {

    //CONSTRUCTORS
    public Admin() {}
}
