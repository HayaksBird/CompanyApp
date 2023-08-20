package com.company.CompanyApp.dao;

import com.company.CompanyApp.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerRepository extends JpaRepository<Manager, Integer> {
}
