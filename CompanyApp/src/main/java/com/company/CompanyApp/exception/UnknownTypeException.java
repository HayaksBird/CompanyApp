package com.company.CompanyApp.exception;

public class UnknownTypeException extends RuntimeException {
    public UnknownTypeException(String type) {
        super(String.format("An unknown type %s. Cannot perform the casting!", type));
    }
}
