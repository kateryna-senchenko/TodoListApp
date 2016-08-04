package com.javaclasses.todolistapp.impl;

import com.javaclasses.todolistapp.AuthenticationException;
import com.javaclasses.todolistapp.RegistrationException;
import com.javaclasses.todolistapp.UserService;
import com.javaclasses.todolistapp.dto.LoginDto;
import com.javaclasses.todolistapp.dto.RegistrationDto;
import com.javaclasses.todolistapp.dto.TokenDto;
import com.javaclasses.todolistapp.dto.UserDto;
import com.javaclasses.todolistapp.entities.Token;
import com.javaclasses.todolistapp.entities.User;
import com.javaclasses.todolistapp.storage.Repository;
import com.javaclasses.todolistapp.storage.TokenRepositoryImpl;
import com.javaclasses.todolistapp.storage.UserRepositoryImpl;
import com.javaclasses.todolistapp.tinytypes.TokenId;
import com.javaclasses.todolistapp.tinytypes.UserId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.javaclasses.todolistapp.ErrorType.*;

/**
 * Implementation of the UserService interface
 */
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private static UserServiceImpl userService = new UserServiceImpl();

    private static Repository<UserId, User> userRepository;
    private static Repository<TokenId, Token> tokenRepository;

    private UserServiceImpl() {
        userRepository = UserRepositoryImpl.getInstance();
        tokenRepository = TokenRepositoryImpl.getInstance();
    }

    public static UserService getInstance() {
        return userService;
    }

    @Override
    public UserId register(RegistrationDto registrationDto) throws RegistrationException {

        checkNotNull(registrationDto.getEmail(),"Email parameter should not be null");
        checkNotNull(registrationDto.getPassword(), "Password parameter should not be null");
        checkNotNull(registrationDto.getConfirmPassword(), "Confirm Password parameter should not be null");


        final String emailPattern = "^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(registrationDto.getEmail());

        if(!matcher.matches()){
            log.error("Failed to register user: invalid email {}", registrationDto.getEmail());
            throw new RegistrationException(INVALID_EMAIL);
        }

        Collection<User> allUsers = userRepository.getAll();

        for (User user : allUsers) {
            if (user.getEmail().equals(registrationDto.getEmail())) {
                log.error("Registration failed: user with email {} already exists", registrationDto.getEmail());
                throw new RegistrationException(DUPLICATE_EMAIL);
            }
        }

        if (registrationDto.getPassword().isEmpty()) {
            log.error("Failed to register user {}: invalid password input", registrationDto.getEmail());
            throw new RegistrationException(PASSWORD_IS_EMPTY);
        }

        if (!(registrationDto.getPassword().equals(registrationDto.getConfirmPassword()))) {
            log.error("Failed to register user {}: passwords do not match", registrationDto.getEmail());
            throw new RegistrationException(PASSWORDS_DO_NOT_MATCH);
        }

        User newUser = new User(registrationDto.getEmail(), registrationDto.getPassword());
        UserId newUserId = userRepository.add(newUser);
        newUser.setId(newUserId);

        if (log.isInfoEnabled()) {
            log.info("Registered user {}", registrationDto.getEmail());
        }

        return newUserId;
    }

    @Override
    public TokenDto login(LoginDto loginDto) throws AuthenticationException {

        checkNotNull(loginDto.getEmail(),"Email parameter should not be null");
        checkNotNull(loginDto.getPassword(), "Password parameter should not be null");

        Collection<User> allUsers = userRepository.getAll();
        User userToLogin = null;

        for (User user : allUsers) {
            if (user.getEmail().equals(loginDto.getEmail())) {
                if (user.getPassword().equals(loginDto.getPassword())) {
                    userToLogin = user;
                    break;
                }
            }
        }

        if (userToLogin == null) {
            log.error("Failed to login user {}", loginDto.getEmail());
            throw new AuthenticationException(AUTHENTICATION_FAILED);
        }

        final Token newToken = new Token(userToLogin.getId());
        final TokenId tokenId = tokenRepository.add(newToken);
        newToken.setTokenId(tokenId);

        if (log.isInfoEnabled()) {
            log.info("Logged in user {}", loginDto.getEmail());
        }

        return new TokenDto(newToken.getTokenId(), newToken.getUserId());
    }

    @Override
    public UserDto findRegisteredUserById(UserId id) {

        User user = userRepository.getItem(id);

        return new UserDto(user.getId(), user.getEmail());
    }

    @Override
    public UserDto findAuthenticatedUserByToken(TokenDto token) {

        Token userToken = tokenRepository.getItem(token.getTokenId());

        return findRegisteredUserById(userToken.getUserId());
    }

    @Override
    public void logout(TokenDto token) {

        tokenRepository.remove(token.getTokenId());

        if (log.isInfoEnabled()) {
            log.info("Logged out user {}", token.getUserId().getId());
        }

    }
}
