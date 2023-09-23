package com.company.CompanyApp.exception;

public class DepartmentNotFoundException extends Exception {
    public DepartmentNotFoundException(int id) {
        super(String.format("A department with id %s does not exist!", id));
    }
}
