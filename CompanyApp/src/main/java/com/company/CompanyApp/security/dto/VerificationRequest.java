package com.company.CompanyApp.security.dto;

import com.company.CompanyApp.security.validation.annotations.MatchCode;
import jakarta.validation.constraints.NotNull;

public class VerificationRequest {
    private static String code;

    @NotNull(message = "Provide a code")
    @MatchCode
    private String inputCode;


    //Setters & Getters;
    public static String getCode() {
        return code;
    }

    public static void setCode(String code) {
        VerificationRequest.code = code;
    }

    public String getInputCode() {
        return inputCode;
    }

    public void setInputCode(String inputCode) {
        this.inputCode = inputCode;
    }
}
