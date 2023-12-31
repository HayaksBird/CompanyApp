package com.company.CompanyApp.security.entity;

import com.company.CompanyApp.app.enums.WorkerType;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "user")
public class User implements UserDetails {
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private WorkerType type;

    /**
     * A user can only be deleted if the worker is deleted. I made sure
     * that the database itself handles that.
     */
    @OneToMany(fetch = FetchType.EAGER,
               cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "user_id",
                referencedColumnName = "id")
    private List<Role> roles;


    //CONSTRUCTORS
    public User() {}


    //Setters & Getters
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> roles = new ArrayList<>();

        for (Role userRole : this.roles) {
            roles.add(new SimpleGrantedAuthority(userRole.getRole()));
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

    public WorkerType getType() { return type; }

    public void setType(WorkerType type) { this.type = type; }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRoles(List<String> roles) {
        this.roles = new LinkedList<>();

        for (String role : roles) {
            this.roles.add(new Role(role, id));
        }
    }
}