package com.javaclasses.todolist;

/**
 * Exception that indicates authentication failure
 */
public class AuthenticationException extends TodoListAppException {

    public AuthenticationException(ErrorType errorType) {
        super(errorType);
    }
}
