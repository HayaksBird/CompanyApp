package com.company.CompanyApp.exception;

public class TypeParseException extends Exception {
    public TypeParseException(String value, String type) {
        super(String.format("%s is not of type %s", value, type));
    }
}
