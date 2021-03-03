package com.example.otp.controllers.wsDto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class UserWsDto implements Serializable {
    private static final long serialVersionUID = -9135173507608150625L;
    long id;
    @NotNull
    @Size(min = 2, max = 40)
    private String name;
    @NotNull
    @Size(min = 2, max = 40)
    private String surname;
    @Email
    @NotNull
    private String email;
    @NotNull
    private LocalDate dateOfBirth;
    @NotNull
    private String maritalStatus;
}
