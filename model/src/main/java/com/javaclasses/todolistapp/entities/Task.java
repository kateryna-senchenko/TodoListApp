package com.javaclasses.todolistapp.entities;

import com.javaclasses.todolistapp.tinytypes.TaskId;
import com.javaclasses.todolistapp.tinytypes.UserId;

import java.time.LocalDate;

/**
 * Task entity
 */
public class Task {

    private TaskId taskId;
    private final UserId userId;
    private final String taskDescription;
    private final LocalDate creationDate;
    private boolean done = false;

    public Task(UserId userId, String taskDescription, LocalDate creationDate) {
        this.userId = userId;
        this.taskDescription = taskDescription;
        this.creationDate = creationDate;
    }

    public void setTaskId(TaskId taskId) {
        this.taskId = taskId;
    }

    public void setDone(boolean done) {
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

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public boolean isDone() {
        return done;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;

        return done == task.done && taskId.equals(task.taskId) && userId.equals(task.userId)
                && taskDescription.equals(task.taskDescription) && creationDate.equals(task.creationDate);

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
