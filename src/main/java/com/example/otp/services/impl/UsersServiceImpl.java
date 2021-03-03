package com.example.otp.services.impl;

import com.example.otp.model.UsersRepository;
import com.example.otp.model.entities.UserEntity;
import com.example.otp.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UsersServiceImpl implements UsersService {

    private UsersRepository usersRepository;

    @Autowired
    public UsersServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserEntity createUser(UserEntity userEntity) {
        Optional<UserEntity> user = usersRepository.findByEmail(userEntity.getEmail());

        if (user.isPresent())
            throw new DuplicateKeyException("User with email " + userEntity.getEmail() + " is present already.");

        return usersRepository.save(userEntity);
    }

    @Override
    public List<UserEntity> findAll() {
        return usersRepository.findAll();
    }

    @Override
    public UserEntity findUserById(long id) {
        return usersRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User was not found by id: " + id));
    }

    @Override
    public UserEntity findUserByEmail(String email) {
        return usersRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User was not found by email: " + email));
    }

    @Override
    public void updateUser(UserEntity userEntity) {
        usersRepository.save(userEntity);
    }

    @Override
    public void deleteUser(long id) {
        UserEntity user = usersRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User was not found by id: " + id));
        usersRepository.delete(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = usersRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("There is no user for the email: " + username));

        return new User(user.getEmail(), null, true, true, true, true, Collections.emptyList());
    }
}
