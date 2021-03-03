package com.example.otp.services.impl;

import com.example.otp.services.OTPGenerator;
import com.example.otp.services.OTPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OTPServiceImpl implements OTPService {
    private OTPGenerator otpGenerator;

    @Autowired
    public OTPServiceImpl(OTPGenerator otpGenerator) {
        this.otpGenerator = otpGenerator;
    }

    @Override
    public String generateOtp(String key) {
        return otpGenerator.generateOTP(key);
    }

    @Override
    public boolean validateOTP(String key, String otpNumber) {
        String cacheOTP = otpGenerator.getOPTByKey(key);

        if (cacheOTP.equals(otpNumber)) {
            otpGenerator.clearOTPFromCache(key);
            return true;
        }

        return false;
    }
}
