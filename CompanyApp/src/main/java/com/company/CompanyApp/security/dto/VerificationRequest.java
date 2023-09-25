package com.company.CompanyApp.security.dto;

import com.company.CompanyApp.validation.annotations.MatchCode;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class VerificationRequest {
    @MatchCode
    @NotNull(message = "Provide a code")
    private String inputCode;
    private List<String> errorMessages;


    //Setters & Getters;
    public String getInputCode() {
        return inputCode;
    }

    public void setInputCode(String inputCode) {
        this.inputCode = inputCode;
    }

    public List<String> getErrorMessages() {
        return errorMessages;
    }

    public void setErrorMessages(List<String> errorMessages) {
        this.errorMessages = errorMessages;
    }
}
