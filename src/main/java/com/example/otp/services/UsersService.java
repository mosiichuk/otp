package com.example.otp.services;

import com.example.otp.model.entities.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface UsersService extends UserDetailsService {
    UserEntity createUser(UserEntity userEntity);

    List<UserEntity> findAll();

    UserEntity findUserById(long id) throws UsernameNotFoundException;

    UserEntity findUserByEmail(String email) throws UsernameNotFoundException;

    void updateUser(UserEntity userEntity);

    void deleteUser(long id);
}
