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
    private final LocalDate dateCreation;
    private boolean done = false;

    public Task(UserId userId, String taskDescription, LocalDate dateCreation) {
        this.userId = userId;
        this.taskDescription = taskDescription;
        this.dateCreation = dateCreation;
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

    public LocalDate getDateCreation() {
        return dateCreation;
    }

    public boolean isDone() {
        return done;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;

        if (done != task.done) return false;
        if (!taskId.equals(task.taskId)) return false;
        if (!userId.equals(task.userId)) return false;
        if (!taskDescription.equals(task.taskDescription)) return false;
        return dateCreation.equals(task.dateCreation);

    }

    @Override
    public int hashCode() {
        int result = taskId.hashCode();
        result = 31 * result + userId.hashCode();
        result = 31 * result + taskDescription.hashCode();
        result = 31 * result + dateCreation.hashCode();
        result = 31 * result + (done ? 1 : 0);
        return result;
    }
}
