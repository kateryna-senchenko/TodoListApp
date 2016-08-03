package com.javaclasses.todolistapp;


import com.javaclasses.todolistapp.dto.LoginDto;
import com.javaclasses.todolistapp.dto.RegistrationDto;
import com.javaclasses.todolistapp.dto.TaskCreationDto;
import com.javaclasses.todolistapp.impl.TaskServiceImpl;
import com.javaclasses.todolistapp.impl.UserServiceImpl;
import com.javaclasses.todolistapp.tinytypes.TaskId;
import com.javaclasses.todolistapp.tinytypes.UserId;
import org.junit.BeforeClass;
import org.junit.Test;

import static com.javaclasses.todolistapp.ErrorType.TASK_DESCRIPTION_IS_EMPTY;
import static org.junit.Assert.*;

public class TaskServiceShould {

    private static final UserService userService = UserServiceImpl.getInstance();
    private final TaskService taskService = TaskServiceImpl.getInstance();

    private static UserId userId;

    @BeforeClass
    public static void registerAndLoginUser() {

        final String email = "Michael.Ross@gmail.com";
        final String password = "FreeMike";

        final RegistrationDto registrationDto = new RegistrationDto(email, password, password);

        try {
            userId = userService.register(registrationDto);
        } catch (RegistrationException e) {
            fail("Failed to register new user");
        }

        final LoginDto loginDto = new LoginDto(email, password);

        try {
            userService.login(loginDto);
        } catch (AuthenticationException e) {
            fail("Failed to login user");
        }
    }

    @Test
    public void createNewTask() {

        final String taskDescription = "Get some air";

        final TaskCreationDto taskCreationData = new TaskCreationDto(userId, taskDescription);

        TaskId taskId = null;
        try {
            taskId = taskService.createTask(taskCreationData);
        } catch (TaskCreationException e) {
            fail("Failed to create new task");
        }

        assertEquals("New task was not created", taskDescription, taskService.findTaskById(taskId).getTaskDescription());
    }

    @Test
    public void failToCreateTaskWithEmptyInput() {

        final String taskDescription = "";

        final TaskCreationDto taskCreationData = new TaskCreationDto(userId, taskDescription);

        try {
            taskService.createTask(taskCreationData);
            fail("Expected TaskCreationException was not thrown");
        } catch (TaskCreationException e) {
            assertEquals(TASK_DESCRIPTION_IS_EMPTY, e.getErrorType());
        }

    }

    @Test
    public void failToCreateTaskWithWhiteSpacesInput() {

        final String taskDescription = "      ";

        final TaskCreationDto taskCreationData = new TaskCreationDto(userId, taskDescription);

        try {
            taskService.createTask(taskCreationData);
            fail("Expected TaskCreationException was not thrown");
        } catch (TaskCreationException e) {
            assertEquals(TASK_DESCRIPTION_IS_EMPTY, e.getErrorType());
        }

    }


    @Test
    public void markTaskAsDone() {

        final String taskDescription = "Go home!";

        final TaskCreationDto taskCreationData = new TaskCreationDto(userId, taskDescription);

        TaskId taskId = null;
        try {
            taskId = taskService.createTask(taskCreationData);
        } catch (TaskCreationException e) {
            fail("Failed to create new task");
        }

        assertFalse("New task is marked as done", taskService.findTaskById(taskId).isDone());

        taskService.markTaskAsDone(taskId);

        assertTrue("Task was not marked as done", taskService.findTaskById(taskId).isDone());
    }

    @Test
    public void undoTask() {

        final String taskDescription = "Buy something to eat";

        final TaskCreationDto taskCreationData = new TaskCreationDto(userId, taskDescription);

        TaskId taskId = null;
        try {
            taskId = taskService.createTask(taskCreationData);
        } catch (TaskCreationException e) {
            fail("Failed to create new task");
        }

        taskService.markTaskAsDone(taskId);
        assertTrue("Task was not marked as done", taskService.findTaskById(taskId).isDone());

        taskService.undoTask(taskId);
        assertFalse("Task was not undone", taskService.findTaskById(taskId).isDone());

    }




}
