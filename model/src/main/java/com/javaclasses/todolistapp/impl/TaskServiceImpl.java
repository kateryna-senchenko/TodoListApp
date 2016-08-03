package com.javaclasses.todolistapp.impl;

import com.javaclasses.todolistapp.TaskCreationException;
import com.javaclasses.todolistapp.TaskService;
import com.javaclasses.todolistapp.dto.TaskCreationDto;
import com.javaclasses.todolistapp.dto.TaskDto;
import com.javaclasses.todolistapp.tinytypes.TaskId;
import com.javaclasses.todolistapp.tinytypes.UserId;

import java.util.List;

/**
 * Implementation of the TaskService interface
 */
public class TaskServiceImpl implements TaskService {

    private static TaskServiceImpl taskService = new TaskServiceImpl();

    private TaskServiceImpl() {}

    public static TaskService getInstance() {
        return taskService;
    }

    @Override
    public TaskId createTask(TaskCreationDto taskCreationDto) throws TaskCreationException {
        return null;
    }

    @Override
    public void markTaskAsDone(TaskId taskId) {

    }

    @Override
    public void undoTask(TaskId taskId) {

    }

    @Override
    public void deleteTask(TaskId taskId) {

    }

    @Override
    public TaskDto findTaskById(TaskId taskId) {
        return null;
    }

    @Override
    public List<TaskDto> findsAllUserTasks(UserId userId) {
        return null;
    }
}
