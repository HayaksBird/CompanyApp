package com.company.CompanyApp.exception;

public class BadLoginInputException extends Exception {
    public BadLoginInputException() {
        super("Wrong username and/or password!");
    }
}
