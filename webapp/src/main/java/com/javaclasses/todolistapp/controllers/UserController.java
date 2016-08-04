package com.javaclasses.todolistapp.controllers;

import com.javaclasses.todolistapp.*;
import com.javaclasses.todolistapp.dto.LoginDto;
import com.javaclasses.todolistapp.dto.RegistrationDto;
import com.javaclasses.todolistapp.dto.TaskDto;
import com.javaclasses.todolistapp.dto.TokenDto;
import com.javaclasses.todolistapp.impl.TaskServiceImpl;
import com.javaclasses.todolistapp.impl.UserServiceImpl;
import com.javaclasses.todolistapp.tinytypes.TokenId;
import com.javaclasses.todolistapp.tinytypes.UserId;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.UUID;

import static com.javaclasses.todolistapp.Parameters.*;
import static com.javaclasses.todolistapp.UrlConstants.LOGIN_URL;
import static com.javaclasses.todolistapp.UrlConstants.LOGOUT_URL;
import static com.javaclasses.todolistapp.UrlConstants.REGISTRATION_URL;


/**
 * Handles requests regarding user management
 */
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private static UserController userController = new UserController();

    private final UserService userService = UserServiceImpl.getInstance();
    private final TaskService taskService = TaskServiceImpl.getInstance();
    private final HandlerRegistry handlerRegistry = HandlerRegistry.getInstance();

    private UserController() {
        register();
        loginUser();
        logoutUser();
    }

    public static UserController getInstance() {
        return userController;
    }

    private void register() {

        handlerRegistry.registerHandler(new CompoundKey(REGISTRATION_URL, "POST"), request -> {

            final String email = request.getParameter(EMAIL);
            final String password = request.getParameter(PASSWORD);
            final String confirmPassword = request.getParameter(CONFIRM_PASSWORD);

            final RegistrationDto registrationDto = new RegistrationDto(email, password, confirmPassword);

            HandlerProcessingResult handlerProcessingResult;
            try {
                UserId userId = userService.register(registrationDto);

                handlerProcessingResult = new HandlerProcessingResult(HttpServletResponse.SC_OK);
                handlerProcessingResult.setData(USER_ID, String.valueOf(userId.getId()));
                handlerProcessingResult.setData(EMAIL, userService.findRegisteredUserById(userId).getEmail());
                handlerProcessingResult.setData(SUCCESS_MESSAGE, "User has been successfully registered");

                if(log.isInfoEnabled()){
                    log.info("Registered user {}", email);
                }

            } catch (RegistrationException e) {

                handlerProcessingResult = new HandlerProcessingResult(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                handlerProcessingResult.setData(ERROR_MESSAGE, e.getMessage());

                if(log.isInfoEnabled()){
                    log.info("Failed to register user {}", email);
                }
            }

            return handlerProcessingResult;
        });
    }

    private void loginUser() {

        handlerRegistry.registerHandler(new CompoundKey(LOGIN_URL, "POST"), request -> {

            final String email = request.getParameter(EMAIL);
            final String password = request.getParameter(PASSWORD);

            final LoginDto loginDto = new LoginDto(email, password);

            HandlerProcessingResult handlerProcessingResult;
            try {
                TokenDto token = userService.login(loginDto);

                List<TaskDto> allUserTasks = taskService.findsAllUserTasks(token.getUserId());
                final JSONArray allTasks = new JSONArray(allUserTasks);

                handlerProcessingResult = new HandlerProcessingResult(HttpServletResponse.SC_OK);
                handlerProcessingResult.setData(TOKEN_ID, String.valueOf(token.getTokenId().getId()));
                handlerProcessingResult.setData(USER_ID, String.valueOf(token.getUserId().getId()));
                handlerProcessingResult.setData(EMAIL, email);
                handlerProcessingResult.setData(TASK_LIST, allTasks.toString());

                if(log.isInfoEnabled()){
                    log.info("Logged in user {}", email);
                }

            } catch (AuthenticationException e) {

                handlerProcessingResult = new HandlerProcessingResult(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                handlerProcessingResult.setData(ERROR_MESSAGE, e.getMessage());

                if(log.isInfoEnabled()){
                    log.info("Failed to login user {}", email);
                }
            }

            return handlerProcessingResult;
        });
    }

    private void logoutUser(){

        handlerRegistry.registerHandler(new CompoundKey(LOGOUT_URL, "POST"), request ->{

            final String tokenId = request.getParameter(TOKEN_ID);
            final String userId = request.getParameter(USER_ID);

            final TokenDto tokenDto =
                    new TokenDto(new TokenId(UUID.fromString(tokenId)), new UserId(Long.valueOf(userId)));

            HandlerProcessingResult handlerProcessingResult;

            if (userService.findAuthenticatedUserByToken(tokenDto) == null) {

                if (log.isInfoEnabled()) {
                    log.info("Forbidden operation");
                }

                handlerProcessingResult = new HandlerProcessingResult(HttpServletResponse.SC_FORBIDDEN);
                handlerProcessingResult.setData(ERROR_MESSAGE, "Cannot find user");
            }

            userService.logout(tokenDto);

            handlerProcessingResult = new HandlerProcessingResult(HttpServletResponse.SC_OK);
            handlerProcessingResult.setData(USER_ID, userId);

            return handlerProcessingResult;

        });
    }

}
