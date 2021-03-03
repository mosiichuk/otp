package com.example.otp.facade.impl;

import com.example.otp.facade.UserFacade;
import com.example.otp.model.entities.UserEntity;
import com.example.otp.services.UsersService;
import com.example.otp.services.dto.UserData;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserFacadeImpl implements UserFacade {
    private UsersService usersService;
    private ModelMapper modelMapper;

    @Autowired
    public UserFacadeImpl(UsersService usersService, ModelMapper modelMapper) {
        this.usersService = usersService;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserData createUser(UserData userData) {
        UserEntity user = modelMapper.map(userData, UserEntity.class);
        return modelMapper.map(usersService.createUser(user), UserData.class);
    }

    @Override
    public UserData findUserByEmail(String email) {
        return modelMapper.map(usersService.findUserByEmail(email), UserData.class);
    }

    @Override
    public UserData findUserById(long id) {
        return modelMapper.map(usersService.findUserById(id), UserData.class);
    }

    @Override
    public List<UserData> findAllUsers() {
        List<UserEntity> users = usersService.findAll();

        return users.stream()
                .map(user -> modelMapper.map(user, UserData.class))
                .collect(Collectors.toList());
    }

    @Override
    public void updateUser(UserData userData) {
        UserEntity user = usersService.findUserById(userData.getId());
        modelMapper.map(userData, user);
        usersService.updateUser(user);
    }

    @Override
    public void deleteUser(long id) {
        usersService.deleteUser(id);
    }
}
