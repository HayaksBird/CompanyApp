package com.company.CompanyApp.exception;

public class WorkerkNotFoundException extends RuntimeException {
    public WorkerkNotFoundException(int id) {
        super(String.format("A worker with id %s does not exist!", id));
    }
}
