package com.example.otp.model.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String email;

    private String name;

    private String surname;

    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    private MaritalStatus maritalStatus;
}