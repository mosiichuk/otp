package com.example.otp.services;

public interface OTPService {
    String generateOtp(String key);

    boolean validateOTP(String key, String otp);
}
