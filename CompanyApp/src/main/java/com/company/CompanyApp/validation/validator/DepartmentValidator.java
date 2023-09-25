package com.company.CompanyApp.validation.validator;

import com.company.CompanyApp.app.service.IDepartmentService;
import com.company.CompanyApp.validation.annotations.Department;
import com.company.CompanyApp.exception.DepartmentNotFoundException;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DepartmentValidator implements ConstraintValidator<Department, Integer> {
    private final IDepartmentService departmentService;


    //CONSTRUCTORS
    public DepartmentValidator(IDepartmentService departmentService) {
        this.departmentService = departmentService;
    }


    @Override
    public void initialize(Department constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }


    @Override
    public boolean isValid(Integer id, ConstraintValidatorContext constraintValidatorContext) {
        try {
            departmentService.getDepartment(id);
            return true;
        } catch (DepartmentNotFoundException ex) {
            return false;
        }
    }
}
