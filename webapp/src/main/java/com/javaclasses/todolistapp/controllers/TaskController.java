package com.javaclasses.todolistapp.controllers;

import com.javaclasses.todolistapp.*;
import com.javaclasses.todolistapp.dto.TaskCreationDto;
import com.javaclasses.todolistapp.dto.TaskDto;
import com.javaclasses.todolistapp.dto.TokenDto;
import com.javaclasses.todolistapp.impl.TaskServiceImpl;
import com.javaclasses.todolistapp.impl.UserServiceImpl;
import com.javaclasses.todolistapp.tinytypes.TaskId;
import com.javaclasses.todolistapp.tinytypes.TokenId;
import com.javaclasses.todolistapp.tinytypes.UserId;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.UUID;

import static com.javaclasses.todolistapp.Parameters.*;
import static com.javaclasses.todolistapp.UrlConstants.*;

/**
 * Handles requests regarding task management
 */
public class TaskController {

    private static final Logger log = LoggerFactory.getLogger(TaskController.class);

    private static TaskController taskController = new TaskController();

    private final UserService userService = UserServiceImpl.getInstance();
    private final TaskService taskService = TaskServiceImpl.getInstance();
    private final HandlerRegistry handlerRegistry = HandlerRegistry.getInstance();

    private TaskController() {
        createTask();
        markTaskAsDone();
        undoTask();
        deleteTask();
    }

    public static TaskController getInstance() {
        return taskController;
    }

    private void createTask() {

        handlerRegistry.registerHandler(new CompoundKey(CREATE_TASK_URL, "POST"), request -> {

            final String tokenId = request.getParameter(TOKEN_ID);
            final String userId = request.getParameter(USER_ID);
            final String taskDescription = request.getParameter(TASK_DESCRIPTION);

            final TokenDto tokenDto =
                    new TokenDto(new TokenId(UUID.fromString(tokenId)), new UserId(Long.valueOf(userId)));

            HandlerProcessingResult handlerProcessingResult;

            if (userService.findAuthenticatedUserByToken(tokenDto) == null) {

                if (log.isInfoEnabled()) {
                    log.info("Forbidden task creation operation");
                }

                handlerProcessingResult = new HandlerProcessingResult(HttpServletResponse.SC_FORBIDDEN);
                handlerProcessingResult.setData(ERROR_MESSAGE, "Cannot find user");
            }

            final TaskCreationDto taskCreationDto =
                    new TaskCreationDto(new UserId(Long.valueOf(userId)), taskDescription);


            try {
                final TaskId taskId = taskService.createTask(taskCreationDto);

                List<TaskDto> allUserTasks = taskService.findsAllUserTasks(new UserId(Long.valueOf(userId)));

                final JSONArray allTasks = new JSONArray(allUserTasks);

                handlerProcessingResult = new HandlerProcessingResult(HttpServletResponse.SC_OK);
                handlerProcessingResult.setData(TOKEN_ID, tokenId);
                handlerProcessingResult.setData(USER_ID, userId);
                handlerProcessingResult.setData(TASK_ID, String.valueOf(taskId.getId()));
                handlerProcessingResult.setData(TASK_DESCRIPTION, taskDescription);
                handlerProcessingResult.setData(TASK_LIST, allTasks.toString());

                if (log.isInfoEnabled()) {
                    log.info("Created task {}", taskId.getId());
                }

            } catch (TaskCreationException e) {

                handlerProcessingResult = new HandlerProcessingResult(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                handlerProcessingResult.setData(ERROR_MESSAGE, e.getMessage());

                if (log.isInfoEnabled()) {
                    log.info("Failed to create task {}", taskDescription);
                }
            }

            return handlerProcessingResult;
        });
    }

    private void markTaskAsDone() {

        handlerRegistry.registerHandler(new CompoundKey(MARK_AS_DONE_URL, "POST"), request -> {

            final String tokenId = request.getParameter(TOKEN_ID);
            final String userId = request.getParameter(USER_ID);
            final String taskId = request.getParameter(TASK_ID);

            final TokenDto tokenDto =
                    new TokenDto(new TokenId(UUID.fromString(tokenId)), new UserId(Long.valueOf(userId)));

            HandlerProcessingResult handlerProcessingResult;

            if (userService.findAuthenticatedUserByToken(tokenDto) == null) {

                if (log.isInfoEnabled()) {
                    log.info("Forbidden operation of marking the task done");
                }

                handlerProcessingResult = new HandlerProcessingResult(HttpServletResponse.SC_FORBIDDEN);
                handlerProcessingResult.setData(ERROR_MESSAGE, "Cannot find user");
            }

            taskService.markTaskAsDone(new TaskId(Long.valueOf(taskId)));
            final TaskDto taskDto = taskService.findTaskById(new TaskId(Long.valueOf(taskId)));

            List<TaskDto> allUserTasks = taskService.findsAllUserTasks(new UserId(Long.valueOf(userId)));

            final JSONArray allTasks = new JSONArray(allUserTasks);

            handlerProcessingResult = new HandlerProcessingResult(HttpServletResponse.SC_OK);
            handlerProcessingResult.setData(TOKEN_ID, tokenId);
            handlerProcessingResult.setData(USER_ID, userId);
            handlerProcessingResult.setData(TASK_ID, taskId);
            handlerProcessingResult.setData(TASK_STATUS, String.valueOf(taskDto.isDone()));
            handlerProcessingResult.setData(TASK_LIST, allTasks.toString());


            if (log.isInfoEnabled()) {
                log.info("Task {} marked as done", taskId);
            }
            return handlerProcessingResult;
        });
    }

    private void undoTask() {

        handlerRegistry.registerHandler(new CompoundKey(UNDO_TASK_URL, "POST"), request -> {

            final String tokenId = request.getParameter(TOKEN_ID);
            final String userId = request.getParameter(USER_ID);
            final String taskId = request.getParameter(TASK_ID);

            final TokenDto tokenDto =
                    new TokenDto(new TokenId(UUID.fromString(tokenId)), new UserId(Long.valueOf(userId)));

            HandlerProcessingResult handlerProcessingResult;

            if (userService.findAuthenticatedUserByToken(tokenDto) == null) {

                if (log.isInfoEnabled()) {
                    log.info("Forbidden operation of marking the task undone");
                }

                handlerProcessingResult = new HandlerProcessingResult(HttpServletResponse.SC_FORBIDDEN);
                handlerProcessingResult.setData(ERROR_MESSAGE, "Cannot find user");
            }

            taskService.undoTask(new TaskId(Long.valueOf(taskId)));
            final TaskDto taskDto = taskService.findTaskById(new TaskId(Long.valueOf(taskId)));

            List<TaskDto> allUserTasks = taskService.findsAllUserTasks(new UserId(Long.valueOf(userId)));

            final JSONArray allTasks = new JSONArray(allUserTasks);

            handlerProcessingResult = new HandlerProcessingResult(HttpServletResponse.SC_OK);
            handlerProcessingResult.setData(TOKEN_ID, tokenId);
            handlerProcessingResult.setData(USER_ID, userId);
            handlerProcessingResult.setData(TASK_ID, taskId);
            handlerProcessingResult.setData(TASK_STATUS, String.valueOf(taskDto.isDone()));
            handlerProcessingResult.setData(TASK_LIST, allTasks.toString());


            if (log.isInfoEnabled()) {
                log.info("Task {} marked as undone", taskId);
            }
            return handlerProcessingResult;
        });
    }

    private void deleteTask() {

        handlerRegistry.registerHandler(new CompoundKey(DELETE_TASK_URL, "POST"), request -> {

            final String tokenId = request.getParameter(TOKEN_ID);
            final String userId = request.getParameter(USER_ID);
            final String taskId = request.getParameter(TASK_ID);

            final TokenDto tokenDto =
                    new TokenDto(new TokenId(UUID.fromString(tokenId)), new UserId(Long.valueOf(userId)));

            HandlerProcessingResult handlerProcessingResult;

            if (userService.findAuthenticatedUserByToken(tokenDto) == null) {

                if (log.isInfoEnabled()) {
                    log.info("Forbidden deletion task operation");
                }

                handlerProcessingResult = new HandlerProcessingResult(HttpServletResponse.SC_FORBIDDEN);
                handlerProcessingResult.setData(ERROR_MESSAGE, "Cannot find user");
            }

            taskService.deleteTask(new TaskId(Long.valueOf(taskId)));

            List<TaskDto> allUserTasks = taskService.findsAllUserTasks(new UserId(Long.valueOf(userId)));

            final JSONArray allTasks = new JSONArray(allUserTasks);

            handlerProcessingResult = new HandlerProcessingResult(HttpServletResponse.SC_OK);
            handlerProcessingResult.setData(TOKEN_ID, tokenId);
            handlerProcessingResult.setData(USER_ID, userId);
            handlerProcessingResult.setData(TASK_LIST, allTasks.toString());


            if (log.isInfoEnabled()) {
                log.info("Deleted task {}", taskId);
            }
            return handlerProcessingResult;
        });
    }
}
