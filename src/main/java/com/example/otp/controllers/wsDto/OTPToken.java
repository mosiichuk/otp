package com.example.otp.controllers.wsDto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OTPToken {
    private String userEmail;
    private String otp;
}
