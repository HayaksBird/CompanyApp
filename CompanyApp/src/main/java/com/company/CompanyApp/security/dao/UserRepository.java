package com.company.CompanyApp.security.dao;


import com.company.CompanyApp.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
