package com.company.CompanyApp.validation.dto;


import java.util.LinkedList;
import java.util.List;

/**
 * A container object that represents an entity that is displayed for
 * view/edition/creation.
 */
public class ModelDataContainer {
    private List<ModelData> modelData;  //List of entity's fields
    private List<String> errorMessages; //Error messages received during edition/creation
    private boolean isOperationSuccessful = false;  //Status message flag


    //CONSTRUCTORS
    public ModelDataContainer(List<ModelData> modelData) {
        this.modelData = modelData;
    }


    public ModelDataContainer() {}


    //Setters & Getters
    public List<String> getErrorMessages() {
        return errorMessages;
    }

    public void setErrorMessages(List<String> errorMessages) {
        this.errorMessages = errorMessages;
    }

    public void setErrorMessage(String errorMessage) {
        errorMessages = new LinkedList<>();

        errorMessages.add(errorMessage);
    }

    public boolean isOperationSuccessful() {
        return isOperationSuccessful;
    }

    public void setOperationSuccessful(boolean operationSuccessful) {
        isOperationSuccessful = operationSuccessful;
    }

    public List<ModelData> getModelData() {
        return modelData;
    }

    public void setModelData(List<ModelData> modelData) {
        this.modelData = modelData;
    }
}
