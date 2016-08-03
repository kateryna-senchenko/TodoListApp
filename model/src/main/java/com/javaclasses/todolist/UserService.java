package com.javaclasses.todolist;

import com.javaclasses.todolist.dto.LoginDto;
import com.javaclasses.todolist.dto.RegistrationDto;
import com.javaclasses.todolist.dto.TokenDto;
import com.javaclasses.todolist.tinytypes.UserId;

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

}
