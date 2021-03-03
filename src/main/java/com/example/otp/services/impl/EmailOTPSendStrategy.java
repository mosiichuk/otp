package com.example.otp.services.impl;

import com.example.otp.services.OTPSendStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailOTPSendStrategy implements OTPSendStrategy {

    private JavaMailSender mailSender;

    @Autowired
    public EmailOTPSendStrategy(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void send(String token, String destination) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(destination);
        message.setSubject("One time password");
        message.setText("Your one time password is - " + token);

        mailSender.send(message);
    }
}
