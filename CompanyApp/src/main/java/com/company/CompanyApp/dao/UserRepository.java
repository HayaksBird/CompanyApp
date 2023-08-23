package com.company.CompanyApp.dao;


import com.company.CompanyApp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
