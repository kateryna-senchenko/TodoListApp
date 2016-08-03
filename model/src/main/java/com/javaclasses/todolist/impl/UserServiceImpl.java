package com.javaclasses.todolist.impl;

import com.javaclasses.todolist.AuthenticationException;
import com.javaclasses.todolist.RegistrationException;
import com.javaclasses.todolist.UserService;
import com.javaclasses.todolist.dto.LoginDto;
import com.javaclasses.todolist.dto.RegistrationDto;
import com.javaclasses.todolist.dto.TokenDto;
import com.javaclasses.todolist.dto.UserDto;
import com.javaclasses.todolist.tinytypes.UserId;

/**
 * Implementation of the UserService interface
 */
public class UserServiceImpl implements UserService {

    private static UserServiceImpl userService = new UserServiceImpl();

    private UserServiceImpl() {}

    public static UserService getInstance() {
        return userService;
    }

    @Override
    public UserId register(RegistrationDto registrationDto) throws RegistrationException {
        return null;
    }

    @Override
    public TokenDto login(LoginDto loginDto) throws AuthenticationException {
        return null;
    }

    @Override
    public UserDto findRegisteredUserById(UserId id) {
        return null;
    }

    @Override
    public UserDto findAuthenticatedUserByToken(TokenDto token) {
        return null;
    }
}
