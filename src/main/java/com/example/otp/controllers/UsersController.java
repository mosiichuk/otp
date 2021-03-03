package com.example.otp.controllers;

import com.example.otp.controllers.wsDto.UserWsDto;
import com.example.otp.facade.UserFacade;
import com.example.otp.services.dto.UserData;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

@RestController
@RequestMapping("/users")
public class UsersController {

    private ModelMapper modelMapper;
    private UserFacade userFacade;

    @Autowired
    public UsersController(ModelMapper modelMapper, UserFacade userFacade) {
        this.modelMapper = modelMapper;
        this.userFacade = userFacade;
    }

    @GetMapping
    public List<UserWsDto> findUsers(@RequestParam(required = false) String email) {
        if (isNotBlank(email)) {
            return Collections.singletonList(modelMapper.map(userFacade.findUserByEmail(email), UserWsDto.class));
        }

        return userFacade.findAllUsers().stream()
                .map(user -> modelMapper.map(user, UserWsDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserWsDto findUser(@PathVariable long id) {
        UserData user = userFacade.findUserById(id);

        return modelMapper.map(user, UserWsDto.class);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserWsDto createUser(@Valid @RequestBody UserWsDto userWsDto) {
        UserData userData = modelMapper.map(userWsDto, UserData.class);

        UserData createdUser = userFacade.createUser(userData);

        return modelMapper.map(createdUser, UserWsDto.class);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable long id) {
        userFacade.deleteUser(id);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateUser(@PathVariable long id, @Valid @RequestBody UserWsDto userWsDto) {
        UserData userData = modelMapper.map(userWsDto, UserData.class);
        userData.setId(id);
        userFacade.updateUser(userData);
    }
}
