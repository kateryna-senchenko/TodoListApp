package com.javaclasses.todolistapp.dto;

import com.javaclasses.todolistapp.tinytypes.UserId;

/**
 * Contains task creation parameters
 */
public class TaskCreationDto {

    private final UserId userId;
    private final String taskDescription;

    public TaskCreationDto(UserId userId, String taskDescription) {
        this.userId = userId;
        this.taskDescription = taskDescription;
    }

    public UserId getUserId() {
        return userId;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TaskCreationDto that = (TaskCreationDto) o;

        return userId.equals(that.userId) && taskDescription.equals(that.taskDescription);

    }

    @Override
    public int hashCode() {
        int result = userId.hashCode();
        result = 31 * result + taskDescription.hashCode();
        return result;
    }
}
