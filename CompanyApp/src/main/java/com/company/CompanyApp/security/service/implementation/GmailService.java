package com.company.CompanyApp.security.service.implementation;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import com.company.CompanyApp.security.service.IGmailService;
import org.springframework.beans.factory.annotation.Value;

/**
 *
 */
@Service
public class GmailService implements IGmailService {
    @Value("${spring.mail.username}")
    private String FROM;
    private final JavaMailSender gmailSender;


    //CONSTRUCTORS
    public GmailService(JavaMailSender gmailSender) {
        this.gmailSender = gmailSender;
    }


    /**
     *
     */
    @Override
    public void sendGmail(String to,
                          String subject,
                          String body) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(FROM);
        message.setTo(to);
        message.setText(body);
        message.setSubject(subject);

        gmailSender.send(message);
    }
}
