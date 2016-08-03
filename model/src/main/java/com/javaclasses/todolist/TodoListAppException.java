package com.javaclasses.todolist;

/**
 * Abstract class for app exceptions
 */
public abstract class TodoListAppException extends Exception{

    private final ErrorType errorType;

    public TodoListAppException(ErrorType errorType) {
        super(errorType.getMessage());
        this.errorType = errorType;
    }

    public ErrorType getErrorType() {
        return errorType;
    }
}
