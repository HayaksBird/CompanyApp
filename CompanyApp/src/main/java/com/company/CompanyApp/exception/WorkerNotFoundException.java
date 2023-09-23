package com.company.CompanyApp.exception;

public class WorkerNotFoundException extends Exception {
    public WorkerNotFoundException(int id) {
        super(String.format("A worker with id %s does not exist!", id));
    }
}
