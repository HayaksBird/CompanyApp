package com.company.CompanyApp.service;

import com.company.CompanyApp.entity.Employer;

import java.util.List;

public interface IEmployerService {
    Employer getEmployer(int id);


    List<Employer> getAllEmployers();


    void addEmployer(Employer employer);


    void updateEmployer(Employer employer);


    void deleteEmployer(int id);


    void deleteAllEmployers();
}
