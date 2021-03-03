package com.example.otp.security;

import com.example.otp.controllers.wsDto.UserWsDto;
import com.example.otp.model.entities.UserEntity;
import com.example.otp.services.OTPSendStrategy;
import com.example.otp.services.OTPService;
import com.example.otp.services.UsersService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private UsersService usersService;
    private OTPService otpService;
    private OTPSendStrategy otpSendStrategy;

    public AuthenticationFilter(UsersService usersService, OTPService otpService, OTPSendStrategy otpSendStrategy) {
        this.usersService = usersService;
        this.otpService = otpService;
        this.otpSendStrategy = otpSendStrategy;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            UserWsDto userDetails = new ObjectMapper().readValue(request.getInputStream(), UserWsDto.class);

            UserEntity userByEmail = usersService.findUserByEmail(userDetails.getEmail());

            return new UsernamePasswordAuthenticationToken(userByEmail.getEmail(), null,
                    Collections.emptyList());
        } catch (IOException e) {
            throw new RuntimeException("Error during login", e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) {
        SecurityContextHolder.getContext().setAuthentication(authResult);
        String otp = otpService.generateOtp(authResult.getName());
        otpSendStrategy.send(otp, authResult.getName());
    }
}
