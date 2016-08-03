package com.javaclasses.todolist;


import com.javaclasses.todolist.dto.LoginDto;
import com.javaclasses.todolist.dto.RegistrationDto;
import com.javaclasses.todolist.dto.TokenDto;
import com.javaclasses.todolist.dto.UserDto;
import com.javaclasses.todolist.impl.UserServiceImpl;
import com.javaclasses.todolist.tinytypes.UserId;
import org.junit.Test;

import static com.javaclasses.todolist.ErrorType.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class UserServiceShould {

    private final UserService userService = UserServiceImpl.getInstance();


    @Test
    public void registerNewUser() {

        final String email = "scout.finch@gmail.com";
        final String password = "freeparrots";

        final RegistrationDto registrationData = new RegistrationDto(email, password, password);

        UserId userId = null;
        try {
            userId = userService.register(registrationData);
        } catch (RegistrationException e) {
            fail("Failed to register new user");
        }

        final UserDto newUser = userService.findRegisteredUserById(userId);

        assertEquals("New user was not registered", email, newUser.getEmail());

    }

    @Test
    public void failRegisterUserWithDuplicateEmail() {

        final String email = "jem.finch@gmail.com";
        final String password = "leavemealone";

        final RegistrationDto registrationData = new RegistrationDto(email, password, password);

        try {
            userService.register(registrationData);
        } catch (RegistrationException e) {
            fail("Failed to register new user");
        }

        try {
            userService.register(registrationData);
            fail("Expected RegistrationException was not thrown");
        } catch (RegistrationException e) {
            assertEquals(DUPLICATE_EMAIL, e.getErrorType());
        }

    }

    @Test
    public void failRegisterIfEmailWithNoAt() {

        final String email = "wrong.gmail.com";
        final String password = "thatcouldhelp";

        final RegistrationDto registrationData = new RegistrationDto(email, password, password);

        try {
            userService.register(registrationData);
            fail("Expected RegistrationException was not thrown");
        } catch (RegistrationException e) {
            assertEquals(INVALID_EMAIL, e.getErrorType());
        }
    }


    @Test
    public void failRegisterUserWithNotMatchingPasswords() {

        final String email = "atticus.finch@gmail.com";
        final String password = "lethimgo";
        final String confirmPassword = password + "123";

        final RegistrationDto registrationData = new RegistrationDto(email, password, confirmPassword);

        try {
            userService.register(registrationData);
            fail("Expected RegistrationException was not thrown");
        } catch (RegistrationException e) {
            assertEquals(PASSWORDS_DO_NOT_MATCH, e.getErrorType());
        }
    }


    @Test
    public void failRegisterUserWithEmptyPassword() {

        final String email = "kevin@gmail.com";
        final String password = "";

        final RegistrationDto registrationData = new RegistrationDto(email, password, password);

        try {
            userService.register(registrationData);
            fail("Expected RegistrationException was not thrown");
        } catch (RegistrationException e) {
            assertEquals(PASSWORD_IS_EMPTY, e.getErrorType());
        }

    }

    @Test
    public void loginUser() {

        final String email = "alice@gmail.com";
        final String password = "followthewhiterabbit";

        final RegistrationDto registrationData = new RegistrationDto(email, password, password);

        try {
            userService.register(registrationData);
        } catch (RegistrationException e) {
            fail("Failed to register new user");
        }

        final LoginDto loginData = new LoginDto(email, password);

        TokenDto token = null;
        try {
            token = userService.login(loginData);
        } catch (AuthenticationException e) {
            fail("Failed to login registered user");
        }

        final UserDto loggedInUser = userService.findAuthenticatedUserByToken(token);

        assertEquals("User was not logged in", email, loggedInUser.getEmail());

    }

    @Test
    public void failLoginUnregisteredUser() {

        final String email = "hatter@gmail.com";
        final String password = "whatawonderfulday";

        final LoginDto loginData = new LoginDto(email, password);

        try {
            userService.login(loginData);
            fail("Expected AuthenticationException was not thrown");
        } catch (AuthenticationException e) {
            assertEquals(AUTHENTICATION_FAILED, e.getErrorType());
        }

    }

    @Test
    public void failLoginUserWithWrongPassword() {

        final String email = "balto@gmail.com";
        final String password = "mush!";

        final RegistrationDto registrationData = new RegistrationDto(email, password, password);

        try {
            userService.register(registrationData);
        } catch (RegistrationException e) {
            fail("Failed to register new user");
        }

        final String wrongPassword = password + "123";
        final LoginDto loginData = new LoginDto(email, wrongPassword);

        try {
            userService.login(loginData);
            fail("Expected AuthenticationException was not thrown");
        } catch (AuthenticationException e) {
            assertEquals(AUTHENTICATION_FAILED, e.getErrorType());
        }

    }
}