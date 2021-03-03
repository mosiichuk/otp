package com.example.otp.services;

public interface OTPSendStrategy {
    void send(String token, String destination);
}
