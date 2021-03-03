package com.example.otp;

import com.example.otp.services.OTPGenerator;
import com.example.otp.services.impl.OTPGeneratorImpl;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

import static org.modelmapper.convention.MatchingStrategies.STRICT;

@SpringBootApplication
public class OtpApplication {
    private Environment environment;

    public OtpApplication(Environment environment) {
        this.environment = environment;
    }

    public static void main(String[] args) {
        SpringApplication.run(OtpApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(STRICT);
        return modelMapper;
    }

    @Bean
    public OTPGenerator otpGenerator() {
        return new OTPGeneratorImpl(new SecureRandom(), Integer.parseInt(environment.getProperty("otp.token.length")),
                CacheBuilder.newBuilder()
                        .expireAfterWrite(Integer.parseInt(environment.getProperty("otp.token.time.expiration.in.minutes")), TimeUnit.MINUTES)
                        .build(new CacheLoader<String, String>() {
                            @Override
                            public String load(String s) throws Exception {
                                return StringUtils.EMPTY;
                            }
                        }));
    }

}
