package com.company.CompanyApp.app.dto;

import java.util.List;

public class WorkerDataContainer {
    private List<WorkerData> workersData;
    private List<String> errorMessages;
    private boolean isAdded = false;


    //CONSTRUCTORS
    public WorkerDataContainer(List<WorkerData> workersData) {
        this.workersData = workersData;
    }


    public WorkerDataContainer(List<WorkerData> workersData, List<String> errorMessages) {
        this.workersData = workersData;
        this.errorMessages = errorMessages;
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

    public boolean isAdded() {
        return isAdded;
    }

    public void setAdded(boolean added) {
        isAdded = added;
    }
}
