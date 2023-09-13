package com.company.CompanyApp.dao;


import com.company.CompanyApp.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
