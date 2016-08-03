package com.javaclasses.todolistapp;

/**
 * Exception that indicates task creation failure
 */
public class TaskCreationException extends TodoListAppException{

    public TaskCreationException(ErrorType errorType) {
        super(errorType);
    }
}
