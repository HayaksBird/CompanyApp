package com.company.CompanyApp.app.dao;

import com.company.CompanyApp.app.entity.worker.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkerRepository extends JpaRepository<Worker, Integer> {
}