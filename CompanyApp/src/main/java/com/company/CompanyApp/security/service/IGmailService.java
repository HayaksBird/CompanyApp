package com.company.CompanyApp.security.service;

public interface IGmailService {
    void sendGmail(String to, String subject, String body);
}
