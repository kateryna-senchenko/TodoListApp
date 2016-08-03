package com.javaclasses.todolistapp;


import org.apache.http.HttpResponse;
import org.json.JSONObject;
import org.junit.Test;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.javaclasses.todolistapp.ErrorType.*;
import static com.javaclasses.todolistapp.Parameters.EMAIL;
import static com.javaclasses.todolistapp.Parameters.ERROR_MESSAGE;
import static com.javaclasses.todolistapp.TestUtils.getResponseContent;
import static com.javaclasses.todolistapp.TestUtils.sendLoginRequest;
import static com.javaclasses.todolistapp.TestUtils.sendRegistrationRequest;
import static org.junit.Assert.assertEquals;

public class UserControllerShould {

    @Test
    public void registerUser() throws IOException {

        final String email = "hello.there@gmail.com";
        final String password = "whatawonderfulday";

        final HttpResponse postResponse = sendRegistrationRequest(email, password, password);
        final JSONObject jsonResult = getResponseContent(postResponse);

        final int expectedStatus = HttpServletResponse.SC_OK;

        assertEquals("Unexpected response status", expectedStatus, postResponse.getStatusLine().getStatusCode());
        assertEquals("Registration request failed", email, jsonResult.optString(EMAIL));

    }

    @Test
    public void failToRegisterUserWithInvalidEmail() throws IOException {

        final String email = "dazy.buchanan.gmail.com";
        final String password = "imbored";


        final HttpResponse postResponse = sendRegistrationRequest(email, password, password);
        final JSONObject jsonResult = getResponseContent(postResponse);

        final int expectedStatus = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
        final String expectedMessage = INVALID_EMAIL.getMessage();

        assertEquals("Unexpected response status", expectedStatus, postResponse.getStatusLine().getStatusCode());
        assertEquals("Registration request failed", expectedMessage, jsonResult.optString(ERROR_MESSAGE));

    }

    @Test
    public void failToRegisterUserWithDuplicateEmail() throws IOException {

        final String email = "nick.carraway@gmail.com";
        final String password = "gonnegtion";

        sendRegistrationRequest(email, password, password);

        final HttpResponse postResponse = sendRegistrationRequest(email, password, password);
        final JSONObject jsonResult = getResponseContent(postResponse);

        final int expectedStatus = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
        final String expectedMessage = DUPLICATE_EMAIL.getMessage();

        assertEquals("Unexpected response status", expectedStatus, postResponse.getStatusLine().getStatusCode());
        assertEquals("Registration request failed", expectedMessage, jsonResult.optString(ERROR_MESSAGE));

    }

    @Test
    public void failToRegisterUserWithEmptyPassword() throws IOException {

        final String email = "dazybuch@gmail.com";
        final String password = "";

        final HttpResponse postResponse = sendRegistrationRequest(email, password, password);
        final JSONObject jsonResult = getResponseContent(postResponse);

        final int expectedStatus = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
        final String expectedMessage = PASSWORD_IS_EMPTY.getMessage();

        assertEquals("Unexpected response status", expectedStatus, postResponse.getStatusLine().getStatusCode());
        assertEquals("Registration request failed", expectedMessage, jsonResult.optString(ERROR_MESSAGE));

    }

    @Test
    public void failToRegisterUserWithNotMatchingPasswords() throws IOException {

        final String email = "dazy.buchanan@gmail.com";
        final String password = "stupidgirl";
        final String confirmPassword = password + "123";

        final HttpResponse postResponse = sendRegistrationRequest(email, password, confirmPassword);
        final JSONObject jsonResult = getResponseContent(postResponse);

        final int expectedStatus = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
        final String expectedMessage = PASSWORDS_DO_NOT_MATCH.getMessage();

        assertEquals("Unexpected response status", expectedStatus, postResponse.getStatusLine().getStatusCode());
        assertEquals("Registration request failed", expectedMessage, jsonResult.optString(ERROR_MESSAGE));

    }

    @Test
    public void loginUser() throws IOException {

        final String email = "jay.gatsby@gmail.com";
        final String password = "youcanrepeatthepast";

        sendRegistrationRequest(email, password, password);

        final HttpResponse postResponse = sendLoginRequest(email, password);

        final JSONObject jsonResult = getResponseContent(postResponse);
        final int expectedStatus = HttpServletResponse.SC_OK;

        assertEquals("Unexpected response status", expectedStatus, postResponse.getStatusLine().getStatusCode());
        assertEquals("Login request failed", email, jsonResult.optString(EMAIL));

    }

    @Test
    public void failToLoginUnregisteredUser() throws IOException {

        final String email = "jordan.b@gmail.com";
        final String password = "letsgo";

        final HttpResponse postResponse = sendLoginRequest(email, password);
        final JSONObject jsonResult = getResponseContent(postResponse);

        final int expectedStatus = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
        final String expectedMessage = AUTHENTICATION_FAILED.getMessage();

        assertEquals("Unexpected response status", expectedStatus, postResponse.getStatusLine().getStatusCode());
        assertEquals("Login request failed", expectedMessage, jsonResult.optString(ERROR_MESSAGE));
    }

    @Test
    public void failToLoginWithWrongPassword() throws IOException {

        final String email = "jordan.baker@gmail.com";
        final String password = "letsgo";
        final String wrongPassword = password + "123";

        sendRegistrationRequest(email, password, password);

        final HttpResponse postResponse = sendLoginRequest(email, wrongPassword);
        final JSONObject jsonResult = getResponseContent(postResponse);

        final int expectedStatus = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
        final String expectedMessage = AUTHENTICATION_FAILED.getMessage();

        assertEquals("Unexpected response status", expectedStatus, postResponse.getStatusLine().getStatusCode());
        assertEquals("Login request failed", expectedMessage, jsonResult.optString(ERROR_MESSAGE));
    }

}
