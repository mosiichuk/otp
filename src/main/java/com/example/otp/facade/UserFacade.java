package com.example.otp.facade;

import com.example.otp.services.dto.UserData;

import java.util.List;

public interface UserFacade {
    UserData createUser(UserData userData);

    UserData findUserByEmail(String email);

    UserData findUserById(long id);

    List<UserData> findAllUsers();

    void updateUser(UserData userData);

    void deleteUser(long id);
}
