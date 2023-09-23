package com.company.CompanyApp.app.dto;

public class WorkerData {
    private String field;
    private String key;
    private String value;



    //CONSTRUCTORS
    public WorkerData(String field,
                      String key,
                      String value) {

        this.field = field;
        this.key = key;
        this.value = value;
    }


    public WorkerData() {}


    //SETTERS & GETTERS
    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
