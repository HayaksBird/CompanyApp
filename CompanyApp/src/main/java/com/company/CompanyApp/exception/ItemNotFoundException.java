package com.company.CompanyApp.exception;

public class ItemNotFoundException extends Exception {
    public ItemNotFoundException(Class<?> itemClass, int id) {
        super(String.format("An item %s with id %s does not exist!", itemClass, id));
    }
}
