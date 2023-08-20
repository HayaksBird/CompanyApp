package com.company.CompanyApp.service.implementation;

import com.company.CompanyApp.dao.EmployerRepository;
import com.company.CompanyApp.entity.Employer;
import com.company.CompanyApp.service.IEmployerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployerService implements IEmployerService {
    private final EmployerRepository EemployerRepository;


    public EmployerService(EmployerRepository EemployerRepository) {
        this.EemployerRepository = EemployerRepository;
    }


    @Override
    public Employer getEmployer(int id) {
        var employer = EemployerRepository.findById(id);

        if (employer.isPresent()) return employer.get();
        else return null;
    }


    @Override
    public List<Employer> getAllEmployers() {
        return EemployerRepository.findAll();
    }


    @Override
    public void addEmployer(Employer employer) {
        EemployerRepository.save(employer);
    }


    @Override
    public void updateEmployer(Employer employer) {
        if (EemployerRepository.findById(employer.getId()).isPresent()) {
            EemployerRepository.save(employer);
        }
    }


    @Override
    public void deleteEmployer(int id) {
        var employer = EemployerRepository.findById(id);

        if (employer.isPresent()) EemployerRepository.delete(employer.get());
    }


    @Override
    public void deleteAllEmployers() {
        EemployerRepository.deleteAll();
    }
}
