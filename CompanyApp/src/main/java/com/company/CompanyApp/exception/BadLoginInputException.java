package com.company.CompanyApp.exception;

public class BadLoginInputException extends RuntimeException {
    public BadLoginInputException() {
        super("Wrong username and/or password!");
    }
}
