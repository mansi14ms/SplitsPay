package com.huassignment.fullstack.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private JavaMailSender emailSender;

    @Autowired
    public EmailService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void sendEmailToUser(String toUser, String subject, String body) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("mytestmail140814@gmail.com");
        message.setTo(toUser);
        message.setSubject(subject);
        message.setText(body);
        emailSender.send(message);

    }
}
