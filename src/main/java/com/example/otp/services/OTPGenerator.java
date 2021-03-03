package com.example.otp.services;

public interface OTPGenerator {
    String generateOTP(String key);

    String getOPTByKey(String key);

    void clearOTPFromCache(String key);
}
