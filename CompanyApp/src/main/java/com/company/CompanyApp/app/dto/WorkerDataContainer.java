package com.company.CompanyApp.app.dto;

import java.util.LinkedList;
import java.util.List;

public class WorkerDataContainer {
    private List<WorkerData> workersData;
    private List<String> errorMessages;
    private boolean isOperationSuccessful = false;


    //CONSTRUCTORS
    public WorkerDataContainer(List<WorkerData> workersData) {
        this.workersData = workersData;
    }


    public WorkerDataContainer() {}


    //Setters & Getters
    public List<WorkerData> getWorkersData() {
        return workersData;
    }

    public void setWorkersData(List<WorkerData> workersData) {
        this.workersData = workersData;
    }

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
}
