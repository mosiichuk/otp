package com.example.otp.services.impl;

import com.example.otp.services.OTPGenerator;
import com.google.common.cache.LoadingCache;
import org.apache.commons.lang3.StringUtils;

import java.util.Random;
import java.util.concurrent.ExecutionException;

public class OTPGeneratorImpl implements OTPGenerator {
    private static final char[] CHARS = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
            'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3',
            '4', '5', '6', '7', '8', '9'};

    private Random rand;
    private int length;
    private LoadingCache<String, String> otpCache;

    public OTPGeneratorImpl(Random rand, int length, LoadingCache<String, String> otpCache) {
        this.rand = rand;
        this.length = length;
        this.otpCache = otpCache;
    }

    @Override
    public String generateOTP(String key) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(CHARS[rand.nextInt(CHARS.length)]);
        }
        String otp = sb.toString();
        otpCache.put(key, otp);
        return otp;
    }

    @Override
    public String getOPTByKey(String key) {
        try {
            return otpCache.get(key);
        } catch (ExecutionException e) {
            return StringUtils.EMPTY;
        }
    }

    @Override
    public void clearOTPFromCache(String key) {
        otpCache.invalidate(key);
    }
}
