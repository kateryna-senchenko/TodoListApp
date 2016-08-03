package com.javaclasses.todolistapp;

import com.javaclasses.todolistapp.dto.LoginDto;
import com.javaclasses.todolistapp.dto.RegistrationDto;
import com.javaclasses.todolistapp.dto.TokenDto;
import com.javaclasses.todolistapp.dto.UserDto;
import com.javaclasses.todolistapp.tinytypes.UserId;

/**
 * User Service public API
 */
public interface UserService {

    /**
     * Registers new user
     * @param registrationDto - contains String email, String password, String confirmPassword
     * @return unique UserId
     * @throws RegistrationException if registration fails
     */
    UserId register(RegistrationDto registrationDto) throws RegistrationException;

    /**
     * Logs in registered user
     * @param loginDto - contains String email and String password
     * @return access Token DTO
     * @throws AuthenticationException if authentication fails
     */
    TokenDto login(LoginDto loginDto) throws AuthenticationException;

    /**
     * Provides access to registered user dto by user id
     * @param id - user id
     * @return user dto
     */
    UserDto findRegisteredUserById(UserId id);

    /**
     * Provides access to authenticated user dto by token
     * @param token - access token DTO
     * @return user dto instance
     */
    UserDto findAuthenticatedUserByToken(TokenDto token);

}
