package com.javaclasses.todolistapp.dto;

import com.javaclasses.todolistapp.tinytypes.TaskId;
import com.javaclasses.todolistapp.tinytypes.UserId;

import java.util.Date;

/**
 * Task DTO
 */
public class TaskDto {

    private final TaskId taskId;
    private final UserId userId;
    private final String taskDescription;
    private final Date dateCreation;
    private final boolean status;

    public TaskDto(TaskId taskId, UserId userId, String taskDescription, Date dateCreation, boolean status) {
        this.taskId = taskId;
        this.userId = userId;
        this.taskDescription = taskDescription;
        this.dateCreation = dateCreation;
        this.status = status;
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

    public Date getDateCreation() {
        return dateCreation;
    }

    public boolean isStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TaskDto taskDto = (TaskDto) o;

        if (status != taskDto.status) return false;
        if (!taskId.equals(taskDto.taskId)) return false;
        if (!userId.equals(taskDto.userId)) return false;
        if (!taskDescription.equals(taskDto.taskDescription)) return false;
        return dateCreation.equals(taskDto.dateCreation);

    }

    @Override
    public int hashCode() {
        int result = taskId.hashCode();
        result = 31 * result + userId.hashCode();
        result = 31 * result + taskDescription.hashCode();
        result = 31 * result + dateCreation.hashCode();
        result = 31 * result + (status ? 1 : 0);
        return result;
    }
}
