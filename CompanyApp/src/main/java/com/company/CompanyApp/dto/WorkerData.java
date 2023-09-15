package com.company.CompanyApp.dto;

public class WorkerData {
    private String key;
    private Object value;


    //CONSTRUCTORS
    public WorkerData(String key,
                      Object value) {

        this.key = key;
        this.value = value;
    }


    //SETTERS & GETTERS
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
