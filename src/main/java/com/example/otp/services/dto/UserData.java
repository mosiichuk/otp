package com.example.otp.services.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class UserData {

    private long id;

    private String email;

    private String name;

    private String surname;

    private LocalDate dateOfBirth;

    private String maritalStatus;
}
