package com.javaclasses.todolistapp.impl;

import com.javaclasses.todolistapp.TaskCreationException;
import com.javaclasses.todolistapp.TaskService;
import com.javaclasses.todolistapp.dto.TaskCreationDto;
import com.javaclasses.todolistapp.dto.TaskDto;
import com.javaclasses.todolistapp.entities.Task;
import com.javaclasses.todolistapp.storage.Repository;
import com.javaclasses.todolistapp.storage.TaskRepositoryImpl;
import com.javaclasses.todolistapp.tinytypes.TaskId;
import com.javaclasses.todolistapp.tinytypes.UserId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.javaclasses.todolistapp.ErrorType.TASK_DESCRIPTION_IS_EMPTY;

/**
 * Implementation of the TaskService interface
 */
public class TaskServiceImpl implements TaskService {

    private static final Logger log = LoggerFactory.getLogger(TaskServiceImpl.class);

    private static TaskServiceImpl taskService = new TaskServiceImpl();

    private static Repository<TaskId, Task> taskRepository;

    private TaskServiceImpl() {
        taskRepository = TaskRepositoryImpl.getInstance();
    }

    public static TaskService getInstance() {
        return taskService;
    }

    @Override
    public TaskId createTask(TaskCreationDto taskCreationDto) throws TaskCreationException {

        checkNotNull(taskCreationDto.getUserId(), "User id should not be null");
        checkNotNull(taskCreationDto.getTaskDescription(), "Task description should not be null");

        final String taskDescription = taskCreationDto.getTaskDescription().trim();

        if(taskDescription.isEmpty()){
            log.error("Failed to create new task: no description");
            throw new TaskCreationException(TASK_DESCRIPTION_IS_EMPTY);
        }

        final Task task = new Task(taskCreationDto.getUserId(), taskDescription, LocalDate.now());
        final TaskId taskId = taskRepository.add(task);
        task.setTaskId(taskId);

        if (log.isInfoEnabled()) {
            log.info("User {} created new task with id {}", taskCreationDto.getUserId().getId(), taskId.getId());
        }

        return taskId;
    }

    @Override
    public void markTaskAsDone(TaskId taskId) {

        final Task task = taskRepository.getItem(taskId);
        task.setDone(true);

        if (log.isInfoEnabled()) {
            log.info("Task {} was marked as done", taskId);
        }

    }

    @Override
    public void undoTask(TaskId taskId) {

        final Task task = taskRepository.getItem(taskId);
        task.setDone(false);

        if (log.isInfoEnabled()) {
            log.info("Task {} was marked undone", taskId.getId());
        }
    }

    @Override
    public void deleteTask(TaskId taskId) {

        taskRepository.remove(taskId);

        if (log.isInfoEnabled()) {
            log.info("Task {} was deleted", taskId.getId());
        }

    }

    @Override
    public TaskDto findTaskById(TaskId taskId) {

        final Task task = taskRepository.getItem(taskId);
        return new TaskDto(task.getTaskId(), task.getUserId(), task.getTaskDescription(),
                        task.getDateCreation(), task.isDone());
    }

    @Override
    public List<TaskDto> findsAllUserTasks(UserId userId) {

        final Collection<Task> allTasks = taskRepository.getAll();
        List<TaskDto> userTasks = new ArrayList<>();

        for(Task task: allTasks){
            if(task.getUserId().equals(userId)){
                userTasks.add(new TaskDto(task.getTaskId(), task.getUserId(),
                        task.getTaskDescription(), task.getDateCreation(), task.isDone()));
            }
        }

        return userTasks;
    }
}
