package com.javaclasses.todolistapp;

import com.javaclasses.todolistapp.dto.TaskCreationDto;
import com.javaclasses.todolistapp.dto.TaskDto;
import com.javaclasses.todolistapp.tinytypes.TaskId;
import com.javaclasses.todolistapp.tinytypes.UserId;

import java.util.List;

/**
 * Task Service public API
 */
public interface TaskService {

    /**
     * Creates new task
     * @param taskCreationDto - contains user id and task description
     * @return task id
     * @throws TaskCreationException if task creation fails
     */
    TaskId createTask(TaskCreationDto taskCreationDto) throws TaskCreationException;

    /**
     * Marks task as done
     * @param taskId - task id
     */
    void markTaskAsDone(TaskId taskId);

    /**
     * Re-opens checked task
     * @param taskId - task id
     */
    void undoTask(TaskId taskId);

    /**
     * Deletes task
     * @param taskId - task id
     */
    void deleteTask(TaskId taskId);

    /**
     * Provides access to task dto by id
     * @param taskId - task id
     * @return task DTO
     */
    TaskDto findTaskById(TaskId taskId);

    /**
     * Provides access to all user tasks
     * @param userId - user id
     * @return list of user tasks
     */
    List<TaskDto> findsAllUserTasks(UserId userId);
}
