package com.javaclasses.todolistapp;


import org.apache.http.HttpResponse;
import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.javaclasses.todolistapp.ErrorType.TASK_DESCRIPTION_IS_EMPTY;
import static com.javaclasses.todolistapp.Parameters.*;
import static com.javaclasses.todolistapp.TestUtils.*;
import static org.junit.Assert.assertEquals;

public class TaskControllerShould {

    private static String tokenId;
    private static String userId;

    @BeforeClass
    public static void registerAndLoginUser() throws IOException {

        final String email = "bob.marley@gmail.com";
        final String password = "jamaicarules";

        sendRegistrationRequest(email, password, password);

        final HttpResponse postResponse = sendLoginRequest(email, password);
        final JSONObject jsonResult = getResponseContent(postResponse);

        tokenId = jsonResult.optString(TOKEN_ID);
        userId = jsonResult.optString(USER_ID);

    }

    @Test
    public void createNewTask() throws IOException {

        final String taskDescription = "Enjoy liquid sunshine";

        final HttpResponse postResponse = sendTaskCreationRequest(tokenId, userId, taskDescription);
        final JSONObject jsonResult = getResponseContent(postResponse);

        final int expectedStatus = HttpServletResponse.SC_OK;

        assertEquals("Unexpected response status", expectedStatus, postResponse.getStatusLine().getStatusCode());
        assertEquals("Failed create chat request", taskDescription, jsonResult.optString(TASK_DESCRIPTION));

    }

    @Test
    public void failToCreateTaskWithEmptyInput() throws IOException {

        final String taskDescription = "";

        final HttpResponse postResponse = sendTaskCreationRequest(tokenId, userId, taskDescription);
        final JSONObject jsonResult = getResponseContent(postResponse);

        final int expectedStatus = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
        final String errorMessage = TASK_DESCRIPTION_IS_EMPTY.getMessage();

        assertEquals("Unexpected response status", expectedStatus, postResponse.getStatusLine().getStatusCode());
        assertEquals("Failed create chat request", errorMessage, jsonResult.optString(ERROR_MESSAGE));

    }

    @Test
    public void failToCreateTaskWithWhiteSpacesInput() throws IOException {

        final String taskDescription = "   ";

        final HttpResponse postResponse = sendTaskCreationRequest(tokenId, userId, taskDescription);
        final JSONObject jsonResult = getResponseContent(postResponse);

        final int expectedStatus = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
        final String errorMessage = TASK_DESCRIPTION_IS_EMPTY.getMessage();

        assertEquals("Unexpected response status", expectedStatus, postResponse.getStatusLine().getStatusCode());
        assertEquals("Failed create chat request", errorMessage, jsonResult.optString(ERROR_MESSAGE));

    }

    @Test
    public void markTaskAsDone() throws IOException {

        final String taskDescription = "Wake up early";

        final HttpResponse firstPostResponse = sendTaskCreationRequest(tokenId, userId, taskDescription);
        final JSONObject firstResult = getResponseContent(firstPostResponse);

        final String taskId = firstResult.optString(TASK_ID);

        final HttpResponse secondPostResponse = sendMarkAsDoneRequest(tokenId, userId, taskId);
        final JSONObject secondResult = getResponseContent(secondPostResponse);

        final int expectedStatus = HttpServletResponse.SC_OK;

        assertEquals("Unexpected response status", expectedStatus, secondPostResponse.getStatusLine().getStatusCode());
        assertEquals("Failed create chat request", "true", secondResult.optString(TASK_STATUS));

    }

    @Test
    public void markTaskAsUndone() throws IOException {

        final String taskDescription = "Go to gym";

        final HttpResponse firstPostResponse = sendTaskCreationRequest(tokenId, userId, taskDescription);
        final JSONObject firstResult = getResponseContent(firstPostResponse);

        final String taskId = firstResult.optString(TASK_ID);

        sendMarkAsDoneRequest(tokenId, userId, taskId);

        final HttpResponse secondPostResponse = sendUndoTaskRequest(tokenId, userId, taskId);
        final JSONObject secondResult = getResponseContent(secondPostResponse);

        final int expectedStatus = HttpServletResponse.SC_OK;

        assertEquals("Unexpected response status", expectedStatus, secondPostResponse.getStatusLine().getStatusCode());
        assertEquals("Failed create chat request", "false", secondResult.optString(TASK_STATUS));

    }
}
