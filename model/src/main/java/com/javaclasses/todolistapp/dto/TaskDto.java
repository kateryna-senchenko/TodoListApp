package com.javaclasses.todolistapp.dto;

import com.javaclasses.todolistapp.tinytypes.TaskId;
import com.javaclasses.todolistapp.tinytypes.UserId;

import java.time.LocalDate;

/**
 * Task DTO
 */
public class TaskDto {

    private final TaskId taskId;
    private final UserId userId;
    private final String taskDescription;
    private final LocalDate creationDate;
    private final boolean done;

    public TaskDto(TaskId taskId, UserId userId, String taskDescription, LocalDate creationDate, boolean done) {
        this.taskId = taskId;
        this.userId = userId;
        this.taskDescription = taskDescription;
        this.creationDate = creationDate;
        this.done = done;
    }

    public TaskId getTaskId() {
        return taskId;
    }

    public UserId getUserId() {
        return userId;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public boolean isDone() {
        return done;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TaskDto taskDto = (TaskDto) o;

        return done == taskDto.done && taskId.equals(taskDto.taskId)
                && userId.equals(taskDto.userId) && taskDescription.equals(taskDto.taskDescription)
                && creationDate.equals(taskDto.creationDate);

    }

    @Override
    public int hashCode() {
        int result = taskId.hashCode();
        result = 31 * result + userId.hashCode();
        result = 31 * result + taskDescription.hashCode();
        result = 31 * result + creationDate.hashCode();
        result = 31 * result + (done ? 1 : 0);
        return result;
    }
}
