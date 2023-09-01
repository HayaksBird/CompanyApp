package com.company.CompanyApp.entity;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "user")
public class User implements UserDetails {
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "password")
    private String password;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id",
                referencedColumnName = "id")
    private List<Role> roles;

    @OneToOne
    @JoinColumn(name = "id",
            referencedColumnName = "id")
    private Worker worker;


    //CONSTRUCTORS
    public User() {}


    //Setters & Getters
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> roles = new ArrayList<>();

        for (Role userRole : this.roles) {
            roles.add(new SimpleGrantedAuthority(userRole.getRole().toString()));
        }

        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return "" + id;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRoles() {
        roles = new ArrayList<Role>();

        switch (worker.getType()) {
            case EMPLOYEE -> roles.add(new Role(WorkerType.EMPLOYEE, id));
            case MANAGER -> {
                roles.add(new Role(WorkerType.EMPLOYEE, id));
                roles.add(new Role(WorkerType.MANAGER, id));
            }
        }
    }
}