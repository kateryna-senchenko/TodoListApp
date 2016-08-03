package com.javaclasses.todolistapp;


import com.javaclasses.todolistapp.dto.LoginDto;
import com.javaclasses.todolistapp.dto.RegistrationDto;
import com.javaclasses.todolistapp.dto.TokenDto;
import com.javaclasses.todolistapp.dto.UserDto;
import com.javaclasses.todolistapp.impl.UserServiceImpl;
import com.javaclasses.todolistapp.tinytypes.UserId;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static com.javaclasses.todolistapp.ErrorType.*;
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

    @Test
    public void beSafeInMultithreading() throws ExecutionException, InterruptedException {

        final int count = 100;
        final ExecutorService executor = Executors.newFixedThreadPool(count);
        final CountDownLatch startLatch = new CountDownLatch(count);
        final List<Future<TokenDto>> results = new ArrayList<>();
        AtomicInteger someDifferenceInEmail = new AtomicInteger(0);

        Callable<TokenDto> callable = () -> {

            startLatch.countDown();
            startLatch.await();

            final String email = "email" + someDifferenceInEmail.getAndIncrement() + "@gmail.com";
            final String password = "password";

            final RegistrationDto registrationData = new RegistrationDto(email, password, password);

            final UserId userId = userService.register(registrationData);

            final LoginDto loginData = new LoginDto(email, password);

            final TokenDto tokenDto = userService.login(loginData);

            assertEquals("User ids after registration and login are not the same", userId, tokenDto.getUserId());

            return tokenDto;
        };

        for (int i = 0; i < count; i++) {

            Future<TokenDto> future = executor.submit(callable);
            results.add(future);
        }

        final Set<Long> userIds = new HashSet<>();
        final Set<UUID> tokenIds = new HashSet<>();

        for (Future<TokenDto> future : results) {
            userIds.add(future.get().getUserId().getId());
            tokenIds.add(future.get().getTokenId().getId());

        }

        if (userIds.size() != count) {
            fail("Generated user ids are not unique");
        }

        if (tokenIds.size() != count) {
            fail("Generated tokens are not unique");
        }

    }
}
