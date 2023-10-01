package com.company.CompanyApp.validation.dto;

/**
 * A dto object for an entity's field.
 */
public class ModelData {
    private String field;
    private String key; //Field's name
    private String value;   //Field's value



    //CONSTRUCTORS
    public ModelData(String field,
                     String key,
                     String value) {

        this.field = field;
        this.key = key;
        this.value = value;
    }


    public ModelData() {}


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
