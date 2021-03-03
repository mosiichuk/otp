package com.example.otp.controllers;

import com.example.otp.controllers.wsDto.OTPToken;
import com.example.otp.services.OTPService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/otp/validate")
public class OTPValidationController {
    private OTPService otpService;
    private Environment environment;

    @Autowired
    public OTPValidationController(OTPService otpService, Environment environment) {
        this.otpService = otpService;
        this.environment = environment;
    }

    @PostMapping
    public ResponseEntity<Object> validateOTP(@RequestBody OTPToken otpToken) {
        String email = otpToken.getUserEmail();

        if (email == null || !otpService.validateOTP(email, otpToken.getOtp())) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        String token = generateJwtToken(email);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("token", token);

        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body(StringUtils.EMPTY);
    }

    private String generateJwtToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(
                        System.currentTimeMillis() + Long.parseLong(environment.getProperty("token.expiration"))))
                .signWith(SignatureAlgorithm.HS512, environment.getProperty("token.secret"))
                .compact();
    }
}
